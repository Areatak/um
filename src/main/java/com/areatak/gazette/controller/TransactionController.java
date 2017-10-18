package com.areatak.gazette.controller;

import com.areatak.gazette.blockchain.ExtendedBitcoinJSONRPCClient;
import com.areatak.gazette.blockchain.ExtendedTxOut;
import com.areatak.gazette.dao.DeviceRepo;
import com.areatak.gazette.dao.MediaRepo;
import com.areatak.gazette.dao.TransactionRepo;
import com.areatak.gazette.dao.UserRepo;
import com.areatak.gazette.entities.Device;
import com.areatak.gazette.entities.Media;
import com.areatak.gazette.entities.Transaction;
import com.areatak.gazette.entities.User;
import com.areatak.gazette.exception.AuthException;
import com.areatak.gazette.metadata.Platform;
import com.areatak.util.Logger;
import com.areatak.gazette.security.Authenticator;
import com.areatak.util.*;
import com.google.gson.Gson;
import com.google.zxing.pdf417.encoder.PDF417;
import com.openhtmltopdf.bidi.support.ICUBidiReorderer;
import com.openhtmltopdf.bidi.support.ICUBidiSplitter;
import com.openhtmltopdf.bidi.support.ICUBreakers;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wf.bitcoin.javabitcoindrpcclient.*;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by farnoosh on 1396/01/26.
 */

@RestController
public class TransactionController {
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private QRUtil qrUtil;
	private static ExtendedBitcoinJSONRPCClient bitcoin = null;
	
	@Autowired
	private DeviceRepo deviceRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private GcmManager gcmManager;
	@Autowired
	private APNsManager apNsManager;
	@Autowired
	private MediaController mediaController;
	@Autowired
	private TransactionRepo transactionRepo;
	@Autowired
	private MediaRepo mediaRepo;
	@Autowired
	private Authenticator authenticator;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	//TODO security considerations
	@RequestMapping(method = RequestMethod.POST, value = "/sold", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> sold(@RequestParam("id") String iaaId) {
		if (StringUtils.isEmpty(iaaId)) {
			Logger.error("TransactionController", "sold", iaaId, "iaaId is empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		UUID uuid;
		try {
			uuid = UUID.fromString(iaaId);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Transaction tx = transactionRepo.findByIaaId(uuid);
		tx.setPaymentStatus(Transaction.PaymentStatus.Paid);
		tx.setPaymentTime(new Date());
		transactionRepo.save(tx);
		HashMap<String, Serializable> result = new HashMap<>();
		result.put("txId", tx.getTxId());
		emailTransactionLinkToUser(tx);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(method = RequestMethod.POST, value = "/createTnx", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> createTnx(@RequestParam("token") String token,
	                                                               @RequestParam("hash") String hash,
	                                                               @RequestParam("type") String type,
	                                                               @RequestParam("name") String name,
	                                                               @RequestParam("subject") String subject,
	                                                               @RequestParam("slogan") String slogan,
	                                                               @RequestParam(value = "desc", required = false) String desc,
	                                                               @RequestParam(name = "files", required = false) /*List<MultipartFile> uploadedFile*/ MultipartFile uploadedFile) throws AuthException {
		synchronized (Spring.lock(token)) {
			desc = StringUtil.convertToUTF8(desc);
			subject = StringUtil.convertToUTF8(subject);
			name = StringUtil.convertToUTF8(name);
			slogan = StringUtil.convertToUTF8(slogan);
			if (CoinUtils.getBitcoinConnection() == null) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if (StringUtils.isEmpty(token)) {
				Logger.error("TransactionController", "createTnx", token, "empty token.");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if (StringUtils.isEmpty(hash)) {
				Logger.error("TransactionController", "createTnx", hash, "empty hash.");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if (StringUtils.isEmpty(type)) {
				Logger.error("TransactionController", "createTnx", type, "empty type.");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			//validate token
			Device device = authenticator.validate(token);
			if (device == null || device.getUser() == null) {
				Logger.error("TransactionController", "createTnx", "", "device or user can not found for this token = " + token);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			Transaction alreadyExist = transactionRepo.findByDataHash(hash);
			if (alreadyExist != null) {
				Logger.info("TransactionController", "createTnx", device.getUser().getEmail(), "already exist");
				return new ResponseEntity<>(alreadyExist.toParamsMap(), HttpStatus.ALREADY_REPORTED);
			}
			/*if (device.getUser().getCredit() < getTransactionFee()) {
			    return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
            }*/
			//try to save the profile pic file in the server
			Media fileMedia = null;
			if (uploadedFile != null && !uploadedFile.isEmpty()) {
				fileMedia = mediaController.saveFile(device.getUser(), FileTypeEnum.UPLOADED_FILE, uploadedFile);
				if (fileMedia == null) {
					Logger.error("TransactionController", "createTnx", "", "file can not be saved.");
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			//this line tis for preventing hibernate.exception.LockAcquisitionException
			//because fileMedia inserted in another thread and here we want to put it as foreign key in our thread
			Media getNewMedia = fileMedia == null ? null : mediaRepo.findOne(fileMedia.getId());

        /* TODO: Farnoosh - 1396/02/15
          first backup the retrieve data in server
         (e.g save them in table with null values for mediaId,txId)
          to ensure data never lost.
          to do that probably txId, dataHash in Transaction should be not unique and this will cause some
          problems in findBy... in TransactionRepo
         */
			
			//try to save the hash in Bitcoin network and get the TxId
			Logger.info("TransactionController", "getBitcoinConnection", "listunspent", "try to get unspent tnx list");
			BitcoindRpcClient.Unspent lastUnspentTnx = getLastUnspentTransaction();
			if (lastUnspentTnx == null) {
				Logger.error("TransactionController", "createTnx", "", "no before transaction found. check the remain amount...");
				return new ResponseEntity<>(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
			}
			
			Logger.info("TransactionController", "createTnx", "lastUnspentTnxId", lastUnspentTnx.txid());
			
			double tnxFee = getTransactionFee() * getTransactionUnit(); //this fee goes to the miner
			
			Logger.info("TransactionController", "createTnx", "Fee", Double.toString(tnxFee));

        /* Farnoosh - 1396/02/15
            in previous solution which Money-MinerFee get and goes to one single address,
            cause a problem which you can not submit new transaction until the last one confirmed.
            means at least 10 min.
         */
			
			List<BitcoindRpcClient.TxInput> inputList = new ArrayList<>();
			List<ExtendedTxOut> outputList = new ArrayList<>();
			
			BitcoindRpcClient.BasicTxInput input1 = new BitcoindRpcClient.BasicTxInput(lastUnspentTnx.txid(), lastUnspentTnx.vout());
			inputList.add(input1);
			
			ExtendedTxOut output1 = new ExtendedTxOut(lastUnspentTnx.address(), lastUnspentTnx.amount() - tnxFee);
			outputList.add(output1);
			
			ExtendedTxOut output2 = new ExtendedTxOut(hash, 0, true);
			outputList.add(output2);
			
			
			String tx = CoinUtils.getBitcoinConnection().createExtendedRawTransaction(inputList, outputList);
			Logger.info("TransactionController", "createTnx", device.getUser().getEmail(), tx);
			
			String signedTx = CoinUtils.getBitcoinConnection().signRawTransaction(tx);
			
			Logger.info("TransactionController", "createTnx", device.getUser().getEmail(), signedTx);
			
			String txId = CoinUtils.getBitcoinConnection().sendRawTransaction(signedTx);
			
			Logger.info("TransactionController", "createTnx", device.getUser().getEmail(), txId);
			Transaction transaction = new Transaction();
			
			transaction.setDesc(desc);
			transaction.setTxId(txId);
			transaction.setDataHash(hash);
			transaction.setFile(getNewMedia);
			transaction.setUser(device.getUser());
			transaction.setConfirmations(0);
			transaction.setConfirmBlockHash("");
			transaction.setConfirmDate(null);
			transaction.setCreated(new Date());
			transaction.setIaaId(UUID.randomUUID());
			transaction.setType(type);
			transaction.setName(name);
			transaction.setSubject(subject);
			transaction.setSlogan(slogan);
			transaction = transactionRepo.save(transaction);
			User user = device.getUser();
//            user.setCredit(user.getCredit() - getTransactionFee());
			userRepo.save(user);
			//TODO REMOVE COMMENT
			emailCreateTransactionToUser(transaction);
			pushCreateTransactionToUser(transaction);
			HashMap<String, Serializable> result = new HashMap<>();
			result.put("txId", txId);
			HashMap <String, Serializable> map = transaction.toParamsMap();
			map.put("txId", "");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/tnxInfoByIaaId", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> tnxInfoByIaaId(@RequestParam("id") String iaaId) {
		if (StringUtils.isEmpty(iaaId)) {
			Logger.error("TransactionController", "tnxInfoByIaaId", iaaId, "empty iaaId");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		UUID uuid;
		try {
			uuid = UUID.fromString(iaaId);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Transaction transaction = transactionRepo.findByIaaId(uuid);
		if (transaction == null) {
			Logger.error("TransactionController", "tnxInfoByIaaId", iaaId, "no transaction found in our server for iaaId=" + iaaId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(transaction.toPaymentBillInfo(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/findUserTnxByTxId", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> findUserTnxByTxId(@RequestParam("token") String token,
	                                                                       @RequestParam("txId") String txId) throws AuthException {
		
		if (StringUtils.isEmpty(token)) {
			Logger.error("TransactionController", "findTnxByTxId", token, "empty token.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (StringUtils.isEmpty(txId)) {
			Logger.error("TransactionController", "findTnxByTxId", txId, "empty transaction Id");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		//validate token
		Device device = authenticator.validate(token);
		if (device == null || device.getUser() == null) {
			Logger.error("TransactionController", "findTnxByTxId", token, "device or user can not found for this token = " + token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		Transaction db_transaction = transactionRepo.findByTxId(txId);
		if (db_transaction == null) {
			Logger.error("TransactionController", "findTnxBydataHash", txId, "no transaction found in our servers for txId=" + txId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		BitcoindRpcClient.RawTransaction transaction = CoinUtils.getBitcoinConnection().getRawTransaction(txId);
		if (transaction == null) {
			Logger.error("TransactionController", "findTnxByTxId", txId, "no transaction found in the network for txId=" + txId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		HashMap<String, Serializable> result = db_transaction.toParamsMap();
		
		return new ResponseEntity<HashMap<String, Serializable>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/findTnxByTxId", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> findTnxByTxId(@RequestParam("txId") String txId) {
		if (StringUtils.isEmpty(txId)) {
			Logger.error("TransactionController", "findTnxByTxId", txId, "empty transaction Id");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Transaction transaction = transactionRepo.findByTxId(txId);
		if (transaction == null) {
			Logger.error("TransactionController", "findTnxByTxId", txId, "no transaction found in our server for txId=" + txId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(transaction.toParamsMap(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/findTnxById", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> findTnxById(@RequestParam("id") String id) {
		if (StringUtils.isEmpty(id)) {
			Logger.error("TransactionController", "findTnxById", id, "empty id");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Transaction transaction = transactionRepo.findById(id);
		if (transaction == null) {
			Logger.error("TransactionController", "findTnxById", id, "no transaction found in our server for id=" + id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		HashMap<String, Serializable> map = transaction.toParamsMap();
		map.remove("txId");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/findTnxBydataHash", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> findTnxBydataHash(@RequestParam("dataHash") String dataHash) {
		if (StringUtils.isEmpty(dataHash)) {
			Logger.error("TransactionController", "findTnxBydataHash", dataHash, "empty transaction Id");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Transaction db_transaction = transactionRepo.findByDataHash(dataHash);
		if (db_transaction == null) {
			Logger.error("TransactionController", "findTnxBydataHash", dataHash, "no transaction found in our servers for data=" + dataHash);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		HashMap<String, Serializable> result = getValidatedTransactionMap(db_transaction);
		
		if (result == null || result.size() == 0) {
			Logger.error("TransactionController", "findTnxBydataHash", dataHash, "no transaction found in the network for data=" + dataHash);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<HashMap<String, Serializable>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/findUserTnxBydataHash", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> findUserTnxBydataHash(@RequestParam("token") String token,
	                                                                           @RequestParam("dataHash") String dataHash) throws AuthException {
		
		if (StringUtils.isEmpty(token)) {
			Logger.error("TransactionController", "findTnxBydataHash", token, "empty token.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (StringUtils.isEmpty(dataHash)) {
			Logger.error("TransactionController", "findTnxBydataHash", dataHash, "empty transaction Id");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Device device = authenticator.validate(token);
		if (device == null || device.getUser() == null) {
			Logger.error("TransactionController", "findTnxBydataHash", token, "device or user can not found for this token = " + token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		Transaction db_transaction = transactionRepo.findByUserAndDataHash(device.getUser(), dataHash);
		if (db_transaction == null) {
			Logger.error("TransactionController", "findTnxBydataHash", dataHash, "no transaction found in our servers for data=" + dataHash);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		HashMap<String, Serializable> result = getValidatedTransactionMap(db_transaction);
		
		if (result == null || result.size() == 0) {
			Logger.error("TransactionController", "findTnxBydataHash", dataHash, "no transaction found in network for dataHash=" + dataHash);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<HashMap<String, Serializable>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/validateTnxBydataHash", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> validateTnxBydataHash(@RequestParam("dataHash") String dataHash) {
		if (StringUtils.isEmpty(dataHash)) {
			Logger.error("TransactionController", "validateTnxBydataHash", dataHash, "empty transaction Id");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Transaction db_transaction = transactionRepo.findByDataHash(dataHash);
		if (db_transaction == null) {
			Logger.error("TransactionController", "validateTnxBydataHash", dataHash, "no transaction found in our servers for data=" + dataHash);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

//        HashMap<String, Serializable> result = getValidatedTransactionMap(db_transaction);
//
//        if (result == null || result.size() == 0) {
//            Logger.error("TransactionController", "validateTnxBydataHash", dataHash, "no transaction found in network for dataHash=" + dataHash);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
		
		return new ResponseEntity<>(db_transaction.toParamsMap(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/listUserTransactions", produces = "application/json; charset=utf-8")
	public ResponseEntity<LinkedHashMap<String, Serializable>> listUserTransactions(@RequestParam("token") String token,
	                                                                                @RequestParam(value = "lastDate", required = false) Number lastDate) throws AuthException {
		
		if (StringUtils.isEmpty(token)) {
			Logger.error("TransactionController", "listTransactions", token, "empty token.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Device device = authenticator.validate(token);
		if (device == null || device.getUser() == null) {
			Logger.error("TransactionController", "listTransactions", token, "device or user can not found for this token = " + token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<Transaction> transactionList = transactionRepo.findByUserAndPaymentStatusAndCreatedGreaterThanEqual(device.getUser(), Transaction.PaymentStatus.Paid, new Date(lastDate == null ? 0 : lastDate.longValue()), new Sort(Sort.Direction.DESC, "created"));
		if (transactionList == null || transactionList.size() == 0) {
			Logger.error("TransactionController", "listTransactions", token, "no transaction found for token = " + token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		Gson gson = new Gson();
		LinkedHashMap<String, Serializable> result = new LinkedHashMap<>();
		for (Transaction transaction : transactionList) {
			HashMap<String, Serializable> txMap = transaction.toParamsMap();
			
			result.put(transaction.getTxId(), txMap);
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getTxFee", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> getTxFee() {
		return new ResponseEntity<String>(new Gson().toJson(getTransactionFee()), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getExchangeRate", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> getExchangeRate() {
		return new ResponseEntity<String>(new Gson().toJson(getCurrencyRate()), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getFinancialUpdate", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> getFinancialUpdate(@RequestParam("token") String token) throws AuthException {
		if (StringUtils.isEmpty(token)) {
			Logger.error("TransactionController", "getFinancialUpdate", token, "empty token.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Device device = authenticator.validate(token);
		if (device == null || device.getUser() == null) {
			Logger.error("TransactionController", "getFinancialUpdate", token, "device or user can not found for this token = " + token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		HashMap<String, Serializable> result = new HashMap<>();
//        result.put("credit",device.getUser().getCredit());
		result.put("fee", getTransactionFee());
		result.put("exchangeRate", getCurrencyRate());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchTransaction", produces = "application/json; charset=utf-8")
	public ResponseEntity<List<HashMap<String, Serializable>>> searchTransaction(@RequestParam("token") String token,
	                                                                             @RequestParam("txId") String txId,
	                                                                             @RequestParam("desc") String desc,
	                                                                             @RequestParam(value = "fromDate", required = false) Long fromDate,
	                                                                             @RequestParam(value = "toDate", required = false) Long toDate) throws AuthException {
		Device device = authenticator.validate(token);
		List<Transaction> transactions = null;
		if (!StringUtils.isEmpty(txId) && !StringUtils.isEmpty(desc)) {
			if (fromDate == null && toDate == null) {
				transactions = transactionRepo.findWithParameter(device.getUser(), Transaction.PaymentStatus.Paid, txId, desc, new Sort(Sort.Direction.DESC, "created"));
			} else {
				transactions = transactionRepo.findWithParameter(device.getUser(), Transaction.PaymentStatus.Paid, txId, desc, fromDate == null ? new Date(0) : new Date(fromDate), toDate == null ? new Date() : new Date(toDate), new Sort(Sort.Direction.DESC, "created"));
			}
		} else if (!StringUtils.isEmpty(txId)) {
			if (fromDate == null && toDate == null) {
				transactions = transactionRepo.findByUserAndPaymentStatusAndTxIdContainingIgnoreCase(device.getUser(), Transaction.PaymentStatus.Paid, txId, new Sort(Sort.Direction.DESC, "created"));
			} else {
				transactions = transactionRepo.findByUserAndPaymentStatusAndTxIdContainingIgnoreCaseAndCreatedBetween(device.getUser(), Transaction.PaymentStatus.Paid, txId, fromDate == null ? new Date(0) : new Date(fromDate), toDate == null ? new Date() : new Date(toDate), new Sort(Sort.Direction.DESC, "created"));
			}
		} else if (!StringUtils.isEmpty(desc)) {
			if (fromDate == null && toDate == null) {
				transactions = transactionRepo.findByUserAndPaymentStatusAndDescContainingIgnoreCase(device.getUser(), Transaction.PaymentStatus.Paid, desc, new Sort(Sort.Direction.DESC, "created"));
			} else {
				transactions = transactionRepo.findByUserAndPaymentStatusAndDescContainingIgnoreCaseAndCreatedBetween(device.getUser(), Transaction.PaymentStatus.Paid, desc, fromDate == null ? new Date(0) : new Date(fromDate), toDate == null ? new Date() : new Date(toDate), new Sort(Sort.Direction.DESC, "created"));
			}
		} else {
			if (fromDate == null && toDate == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {
				transactions = transactionRepo.findByUserAndPaymentStatusAndCreatedBetween(device.getUser(), Transaction.PaymentStatus.Paid, fromDate == null ? new Date(0) : new Date(fromDate), toDate == null ? new Date() : new Date(toDate), new Sort(Sort.Direction.DESC, "created"));
			}
		}
		
		List<HashMap<String, Serializable>> list = transactions.parallelStream().map(Transaction::toParamsMap).collect(Collectors.toList());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/downloadTnxFile/{token}&{mediaId}")
	public ResponseEntity<Resource> downloadTnxFile(@PathVariable(name = "token") String token,
	                                                @PathVariable(name = "mediaId") String mediaId) throws AuthException {
		Device device = authenticator.validate(token);
		if (device == null || device.getUser() == null) {
			Logger.info("TransactionController", "downloadTnxFile", "token", "device or user not found with token = " + token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		File file = mediaController.getFileByMediaId(mediaId);
		Media media = mediaRepo.findOne(mediaId);
		
		if (file == null || !file.exists()) {
			Logger.info("TransactionController", "downloadTnxFile", "mediaId", "no media found for id = " + mediaId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		try {
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok()
					.contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getFileName() + "\"")
					.body(resource);
			
		} catch (IOException e) {
			e.printStackTrace();
			Logger.info("TransactionController", "downloadTnxFile", "", "internal io exception in server for media id = " + mediaId);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Scheduled(fixedRate = 60000)
	public void checkConfirmationJob() {
//        Logger.info("TransactionController", "checkConfirmationJob", "", "check for confirmation job started.");
		
		List<Transaction> unconfirmedTransactions = transactionRepo.findAllByConfirmations(0);
		
		if (unconfirmedTransactions != null && unconfirmedTransactions.size() > 0)
			Logger.info("TransactionController", "checkConfirmationJob", "", "unconfirmed transactions found, #=" + unconfirmedTransactions.size());
		
		for (Transaction tx : unconfirmedTransactions) {
			BitcoindRpcClient.RawTransaction rawTransaction = CoinUtils.getBitcoinConnection().getRawTransaction(tx.getTxId());
			if (rawTransaction != null) {
				if (!StringUtils.isEmpty(rawTransaction.blockHash())) {
					//TODO: Farnoosh 13960202: rawTransaction.confirmations throw exception when
					//tx is pending, so we check rawTransaction.blockHash to ensure transaction puts in a block
					//and after that we ensure "confirmations" key exists in the rawTransaction Map and no exception thrown.
					if (rawTransaction.confirmations() > 0) {
						tx.setConfirmations(rawTransaction.confirmations());
						tx.setConfirmDate(rawTransaction.blocktime());
						tx.setConfirmBlockHash(rawTransaction.blockHash());
						tx = transactionRepo.save(tx);
						emailPaymentLinkToUser(tx);
//                        emailSubmitTransactionToUser(tx);
						pushSubmitTransactionToUser(tx);
					}
				}
			}
		}
		//TODO: Farnoosh 13960202: if we wants more than 1 confirmations we should list all transactions in db
		//with confirmations below than our desire confirmations.
//        Logger.info("TransactionController", "checkConfirmationJob", "", "check for confirmation job ended.");


//       Commented by Farnoosh 13960202: this listeners not working and
//          this BitcoinAcceptor class probably for receive coins

//        if(acceptor == null)
//            acceptor = new BitcoinAcceptor(CoinUtils.getBitcoinConnection(),new ConfirmedPaymentListener(1) {
//            @Override
//            public void confirmed(BitcoindRpcClient.Transaction transaction) {
//                Transaction db_transaction = transactionRepo.findByTxId(transaction.txId());
//                if(db_transaction != null){
//                    db_transaction.setConfirmed(true);
//                    transactionRepo.save(db_transaction);
//                }
//                List unconfirmed = transactionRepo.findAllByConfirmed(false);
//                if(unconfirmed == null || unconfirmed.size() == 0)
//                    acceptor.stopAccepting();
//            }
//        });
//        acceptor.run();
	}
	
	private void emailPaymentLinkToUser(Transaction tx) {
		String userId = tx.getUser().getId();
		try {
//			final String txLink = "http://utadoc.com/#/transaction/detail/" + tx.getTxId();
			final String link = "http://payment.ballyhooawards.ir?id=" + tx.getIaaId();
			final String mailTitle = Spring.context.getMessage("transactionPaymentMailSubject",
					null,
					Locale.forLanguageTag("en"));
			final String mailBody = Spring.context.getMessage("transactionPaymentMailMessage",
					new Object[]{link},
					Locale.forLanguageTag("en"));
//            ByteArrayOutputStream bos = createPdf(tx);
			mailUtil.sendMail(tx.getUser().getEmail(),
					mailTitle,
					mailBody,
					"QRCode",
					qrUtil.generate(link));
		} catch (Exception ex) {
			Logger.error("TransactionController", "emailPaymentLinkToUser", tx.getUser().getEmail(), ex.getMessage(), ex);
		}
	}
	
	private void emailSubmitTransactionToUser(Transaction tx) {
		try {
			final String link = "http://utadoc.com/#/transaction/detail/" + tx.getTxId();
			final String mailTitle = Spring.context.getMessage("transactionSubmitMailSubject",
					null,
					Locale.forLanguageTag("en"));
			final String mailBody = Spring.context.getMessage("transactionSubmitMailMessage",
					new Object[]{tx.getTxId(), link},
					Locale.forLanguageTag("en"));
			ByteArrayOutputStream bos = createPdf(tx);
			mailUtil.sendMail(tx.getUser().getEmail(),
					mailTitle,
					mailBody,
					"QRCode",
					qrUtil.generate(link),
					"transaction.pdf",
					bos.toByteArray());
		} catch (Exception ex) {
			Logger.error("TransactionController", "emailSubmitTransactionToUser", tx.getUser().getEmail(), ex.getMessage(), ex);
		}
	}
	
	private ByteArrayOutputStream createPdf(Transaction tx) throws Exception {
		final String link = "http://utadoc.com/#/transaction/detail/" + tx.getTxId();
		final String html = "<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				"<meta charset='UTF-8'></meta>" +
				"<title>Transaction</title>" +
				"<style>" +
				"@font-face {" +
				"font-family: 'cjk';" +
				"src: url('/resources/font/Lato/Lato-Regular.ttf');" +
				"}" +
				"body{" +
				"font-family:cjk;" +
				"}" +
				"</style>" +
				"</head>" +
				"<body>" +
				"<center>" +
				"<div>" +
				"<img width='50px' src='/resources/images/logo-condensed.png'/>" +
				"</div>" +
				"</center>" +
				"<center>" +
				"<div>Utadoc</div>" +
				"</center>" +
				"<br/><br/>" +
				"<div style='font-weight: bold'>Transaction details</div>" +
				"<br/>" +
				"<div>" +
				"<div>This transaction created at " + tx.getCreated() + " </div>" +
				"<div>Id: " + tx.getTxId() + "</div>" +
				"<br/>" +
				"<center>" +
				"<div>" +
				"<img src='data:image/png;base64," + Base64.getEncoder().encodeToString(qrUtil.generate(link, 150)) + "'/>" +
				"</div>" +
				"</center>" +
				"</div>" +
				"<br/>" +
				"<div>For more details open this url " +
				"</div>" +
				"<div>" +
				link +
				"</div>" +
				"</body>" +
				"</html>";
		PdfRendererBuilder builder = new PdfRendererBuilder();
		builder.useUnicodeBidiSplitter(new ICUBidiSplitter.ICUBidiSplitterFactory());
		builder.useUnicodeBidiReorderer(new ICUBidiReorderer());
		builder.useUnicodeCharacterBreaker(new ICUBreakers.ICUCharacterBreaker(Locale.forLanguageTag("en")));
		builder.defaultTextDirection(PdfRendererBuilder.TextDirection.LTR);
		builder.withHtmlContent(html, "http://localhost:8080/");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		builder.toStream(bos);
		builder.run();
		return bos;
	}
	
	private void emailCreateTransactionToUser(Transaction tx) {
		try {
//			final String link = "http://utadoc.com/#/transaction/detail/" + tx.getTxId();
//			final String paymentLink = "http://payment.ballyhooawards.ir?id=" + tx.getIaaId();
			final String mailTitle = Spring.context.getMessage("transactionCreateMailSubject",
					null,
					Locale.forLanguageTag("en"));
			final String mailBody = Spring.context.getMessage("transactionCreateMailMessage",
					new Object[]{tx.getUser().getEmail()},
					Locale.forLanguageTag("en"));
			ByteArrayOutputStream bos = createPdf(tx);
			mailUtil.sendMail(tx.getUser().getEmail(),
					mailTitle,
					mailBody
//					"QRCode",
//					qrUtil.generate(paymentLink),
					/*"transaction.pdf", bos.toByteArray()*/);
		} catch (Exception ex) {
			Logger.error("TransactionController", "emailCreateTransactionToUser", tx.getUser().getEmail(), ex.getMessage(), ex);
		}
	}
	
	private void emailTransactionLinkToUser(Transaction tx) {
		try {
			final String link = "http://utadoc.com/#/transaction/detail/" + tx.getTxId();
			final String mailTitle = Spring.context.getMessage("transactionLinkMailSubject",
					null,
					Locale.forLanguageTag("en"));
			final String mailBody = Spring.context.getMessage("transactionLinkMailMessage",
					new Object[]{tx.getUser().getEmail(), link},
					Locale.forLanguageTag("en"));
			ByteArrayOutputStream bos = createPdf(tx);
			mailUtil.sendMail(tx.getUser().getEmail(),
					mailTitle,
					mailBody,
					"QRCode",
					qrUtil.generate(link),
					"transaction.pdf", bos.toByteArray());
		} catch (Exception ex) {
			Logger.error("TransactionController", "emailTransactionLinkToUser", tx.getUser().getEmail(), ex.getMessage(), ex);
		}
	}
	
	private void pushSubmitTransactionToUser(Transaction tx) {
		final String pushTitle = Spring.context.getMessage("transactionSubmitPushSubject",
				null,
				Locale.forLanguageTag("en"));
		final String pushBody = Spring.context.getMessage("transactionSubmitPushMessage",
				new Object[]{tx.getTxId()},
				Locale.forLanguageTag("en"));
		final List<Device> deviceList = tx.getUser().getDeviceList();
		for (Device device : deviceList) {
			if (device.getPlatform() == Platform.IOS && !StringUtils.isEmpty(device.getCid())) {
				apNsManager.sendNotificationTo(pushTitle, pushBody, new Gson().toJson(tx.toParamsMap()), device.getCid());
			} else if (device.getPlatform() == Platform.ANDROID && !StringUtils.isEmpty(device.getFid())) {
				gcmManager.sendNotificationTo(pushTitle, pushBody, new Gson().toJson(tx.toParamsMap()), device.getFid());
			}
		}
	}
	
	
	private void pushCreateTransactionToUser(Transaction tx) {
		final String pushTitle = Spring.context.getMessage("transactionCreatePushSubject",
				null,
				Locale.forLanguageTag("en"));
		final String pushBody = Spring.context.getMessage("transactionCreatePushMessage",
				new Object[]{tx.getUser().getEmail(), tx.getTxId()},
				Locale.forLanguageTag("en"));
		final List<Device> deviceList = tx.getUser().getDeviceList();
		for (Device device : deviceList) {
			if (device.getPlatform() == Platform.IOS && !StringUtils.isEmpty(device.getCid())) {
				apNsManager.sendNotificationTo(pushTitle, pushBody, new Gson().toJson(tx.toParamsMap()), device.getCid());
			} else if (device.getPlatform() == Platform.ANDROID && !StringUtils.isEmpty(device.getFid())) {
				gcmManager.sendNotificationTo(pushTitle, pushBody, new Gson().toJson(tx.toParamsMap()), device.getFid());
			}
		}
	}
	
	/**
	 * find the last unspent transaction which hash enough amount.
	 *
	 * @return transaction id and EMPTY string if nothing found
	 */
	private BitcoindRpcClient.Unspent getLastUnspentTransaction() {
		List<BitcoindRpcClient.Unspent> unspentList = CoinUtils.getBitcoinConnection().listUnspent();
		if (unspentList != null && unspentList.size() > 0) {
			for (BitcoindRpcClient.Unspent unspent : unspentList) {
				if (unspent.amount() > getTransactionFee() * getTransactionUnit()) {
					return unspent;
				}
			}
		}
		return null;
	}
	
	/**
	 * find the fee of every transaction which normally get by miners
	 *
	 * @return the transaction fee in Satoshi
	 */
	public static double getTransactionFee() {
		return 5;
	}
	
	public double getTransactionUnit() {
		return 0.0001;
	}
	
	public static double getCurrencyRate() {
		return 5;
	}
	
	
	/**
	 * check the existence of tx_id in Bitcoin network and
	 * collect all the validated transactions in a map
	 *
	 * @param db_transaction transaction to be validated
	 * @return HashMap of validated transactions in the Bitcoin network
	 */
	private HashMap<String, Serializable> getValidatedTransactionMap(Transaction db_transaction) {
		HashMap<String, Serializable> result = new HashMap<>();
		
		if (!StringUtils.isEmpty(db_transaction.getTxId())) {
			String txId = db_transaction.getTxId();
			
			BitcoindRpcClient.RawTransaction transaction = CoinUtils.getBitcoinConnection().getRawTransaction(txId);
			
			if (transaction == null) {
				Logger.error("TransactionController", "getValidatedTransactionMap", "", "no transaction found in network for txId=" + txId);
			}
			result.putAll(db_transaction.toParamsMap());
		}
		
		return result;
	}
}
