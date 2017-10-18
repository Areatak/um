package com.areatak.gazette.listeners;

import com.areatak.gazette.blockchain.ExtendedBitcoinJSONRPCClient;
import com.areatak.gazette.dao.CoinPaymentRepo;
import com.areatak.gazette.dao.UserRepo;
import com.areatak.gazette.entities.CoinPayment;
import com.areatak.gazette.entities.User;
import com.areatak.gazette.metadata.CoinType;
import com.areatak.util.CoinUtils;
import com.areatak.util.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import wf.bitcoin.javabitcoindrpcclient.BitcoinAcceptor;
import wf.bitcoin.javabitcoindrpcclient.BitcoinPaymentListener;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alirezaghias on 7/4/2017 AD.
 */
public class BitcoinListener {
	@Autowired
	private CoinPaymentRepo coinPaymentRepo;
	@Autowired
	private UserRepo userRepo;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private BitcoinAcceptor bitcoinAcceptor;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	@PostConstruct
	public void init() {
		ExtendedBitcoinJSONRPCClient bitcoin = CoinUtils.getBitcoinConnection();
		if (bitcoin != null) {
			Logger.info("", "start listening...");
			if (bitcoinAcceptor == null) {
				bitcoinAcceptor = new BitcoinAcceptor(bitcoin, new BitcoinPaymentListener() {
					@Override
					public void block(String s) {
					
					}
					
					@Override
					public void transaction(BitcoindRpcClient.Transaction transaction) {
						executorService.execute(new Runnable() {
							@Override
							public void run() {
								process(transaction);
							}
						});
					}
				});
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						bitcoinAcceptor.run();
					}
				});
				
				
			}
		}
	}
	
	@Transactional
	private void process(BitcoindRpcClient.Transaction transaction) {
		Logger.info(transaction.address(), "processing...");
		CoinPayment payment = coinPaymentRepo.findByAddressAndCoinType(transaction.address(), CoinType.Utabit);
		if (payment != null) {
			if (payment.getStatus() == CoinPayment.Status.Listening || payment.getStatus() == CoinPayment.Status.NotConfirmedYet) {
				if (transaction.confirmations() > 0) {
					payment.setPaidAmount(transaction.amount());
					if (transaction.amount() >= payment.getAmount()) {
						payment.setStatus(CoinPayment.Status.Paid);
						payment.setConfirmations(transaction.confirmations());
						payment.setTxId(transaction.txId());
						coinPaymentRepo.save(payment);
						Logger.info(transaction.address(), "paid.");
						User user = payment.getUser();
//                                                user.setCredit(user.getCredit() + payment.getPaidAmount());
						userRepo.save(user);
						if (!StringUtils.isEmpty(payment.getStompId())) {
							simpMessagingTemplate.convertAndSendToUser(payment.getStompId(), "/queue/received", payment.toParamsMap());
						}
						
					} else {
						payment.setStatus(CoinPayment.Status.AmountMisMatched);
						coinPaymentRepo.save(payment);
						Logger.info(transaction.address(), "amount mismatched");
					}
				} else {
					payment.setStatus(CoinPayment.Status.NotConfirmedYet);
					coinPaymentRepo.save(payment);
					Logger.info(transaction.address(), "not confirmed yet.");
				}
			} else {
				Logger.info(transaction.address(), "paymentRequest status is " + payment.getStatus().name());
			}
		} else {
			Logger.info(transaction.address(), "no payment found.");
		}
	}
	
}