package com.areatak.gazette.controller;

import com.areatak.gazette.dao.DeviceRepo;
import com.areatak.gazette.dao.UserRepo;
import com.areatak.gazette.entities.Device;
import com.areatak.gazette.entities.Media;
import com.areatak.gazette.entities.User;
import com.areatak.gazette.exception.AuthException;
import com.areatak.gazette.metadata.Platform;
import com.areatak.gazette.security.Authenticator;
import com.areatak.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.map.LRUMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Farnoosh on 1396/02/06.
 */
@RestController
public class UserController {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private DeviceRepo deviceRepo;
	@Autowired
	private MediaController mediaController;
	@Autowired
	private Authenticator authenticator;
	@Autowired
	private MailUtil mailUtil;
	@Value("${initialCredit}")
	private double initalCredit;
	@Value("${lowCredit}")
	private double lowCredit;
	@Value("${inviteCredit}")
	private double inviteCredit;
	
	public static LRUMap<String, Object> forgotPassword = new LRUMap<>(1000);
	private Random random = new Random(new Date().getTime());


	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/oauth", produces = "application/json; charset=utf-8")
	public ResponseEntity<Boolean> hasAuth(@RequestParam String token) {
		try {
			authenticator.validate(token);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} catch (Exception ignore) {
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);

	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/signup", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> singup(@RequestParam(name = "email") String email,
	                                                            @RequestParam(name = "passwordHash") String passwordHash,
	                                                            @RequestParam(name = "confirmPasswordHash") String confirmPasswordHash,
	                                                            @RequestParam(name = "name") String name,
	                                                            @RequestParam(name = "lastName") String lastName,
	                                                            @RequestParam(name = "job") String job,
	                                                            @RequestParam(name = "phone") String phone,
	                                                            @RequestParam(name = "companyCountry", required = false) String country,
	                                                            @RequestParam(name = "companyActivity", required = false) String companyActivity,
	                                                            @RequestParam(name = "companyName", required = false) String companyName,
	                                                            @RequestParam(name = "postalAddress", required = false) String postalAddress,
	                                                            @RequestParam(name = "city", required = false) String city,
	                                                            @RequestParam(name = "postCode", required = false) String postCode,
	                                                            @RequestParam(name = "companyPhone", required = false) String companyPhone,
	                                                            @RequestParam(name = "companyWebsite", required = false) String companyWebsite,
	                                                            @RequestParam(name = "cdName") String cdName,
	                                                            @RequestParam(name = "cdLastName") String cdLastName,
	                                                            @RequestParam(name = "platform") String strPlatform,
	                                                            @RequestParam(name = "inviteFrom", required = false) String inviteFrom) {
		Platform platform = Platform.getPlatform(strPlatform);
		
		if (StringUtils.isEmpty(email)) {
			Logger.info("UserController", "signup", email, "email is empty");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		if (StringUtils.isEmpty(passwordHash) || StringUtils.isEmpty(confirmPasswordHash)) {
			Logger.info("UserController", "signup", passwordHash, "password is empty");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		if (!passwordHash.equals(confirmPasswordHash)) {
			Logger.info("UserController", "signup", passwordHash, "password and confirm password not equal");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User duplicatedUser = userRepo.findByEmail(email);
		if (duplicatedUser != null) {
			Logger.info("UserController", "signup", email, "this email is already signed up.");
			return new ResponseEntity<>(HttpStatus.IM_USED);
		}
		//TODO: Fanroosh 960126 - maybe in future there will be a sort of confirmation email
		
		User user = new User();
		user.setEmail(email);
		user.setPasswordHash(passwordHash);
		user.setCdFirstName(cdName);
		user.setCdLastName(cdLastName);
		user.setCity(city);
		if (companyActivity != null) {
			user.setCompanyActivity(User.companyActivity.valueOf(companyActivity));
		}
		user.setCompanyName(companyName);
		user.setCompanyPhone(companyPhone);
		user.setCompanyWebsite(companyWebsite);
		user.setCountry(country);
		user.setJob(job);
		user.setLastName(lastName);
		user.setName(name);
		user.setPhone(phone);
		user.setPostalAddress(postalAddress);
		user.setPostCode(postCode);
		user = userRepo.save(user);
		
		Device device = new Device();
		device.setPlatform(platform);
		device.setUser(user);
		device.setDeviceId("User_" + platform.name());
		device.setToken(DigestUtils.sha1Hex(UUID.randomUUID().toString()));
		deviceRepo.save(device);
		
		HashMap<String, Serializable> userInfo = user.toParamsMap();
		userInfo.put("token", device.getToken());
		
		try {
			mailUtil.sendMail(user.getEmail(),
					Spring.context.getMessage("signupMailSubject", null, Locale.forLanguageTag("en")),
					Spring.context.getMessage("signupMailMessage", new Object[]{user.getEmail()}, Locale.forLanguageTag("en")),
					"", null);
			if (!StringUtils.isEmpty(inviteFrom)) {
				User inviteUser = userRepo.findOne(inviteFrom);
				if (inviteUser != null) {
//                    inviteUser.setCredit(inviteUser.getCredit() + inviteCredit);
					userRepo.save(inviteUser);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/forgotPassword", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> forgotPassword(@RequestParam(name = "email") String email) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			String hash = DigestUtils.sha256Hex(UUID.randomUUID().toString());
			forgotPassword.put(hash, user.getEmail());
			mailUtil.sendMail(user.getEmail(),
					Spring.context.getMessage("forgotPassSubject", null, Locale.forLanguageTag("en")),
					Spring.context.getMessage("forgotPassMail", new Object[]{hash}, Locale.forLanguageTag("en")),
					"", null);
		}
		return new ResponseEntity<HashMap<String, Serializable>>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/changePass", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> changePass(@RequestParam(name = "h") String hash,
	                                                                @RequestParam(name = "passwordHash") String passwordHash) {
		String email = (String) forgotPassword.remove(hash);
		if (email == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			User user = userRepo.findByEmail(email);
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			} else {
				user.setPasswordHash(passwordHash);
				user = userRepo.save(user);
				return new ResponseEntity<>(user.toParamsMap(), HttpStatus.OK);
			}
		}
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/login", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> login(@RequestParam(name = "email") String email,
	                                                           @RequestParam(name = "passwordHash") String passwordHash,
	                                                           @RequestParam(name = "platform") String strPlatform) {
		Platform platform = Platform.getPlatform(strPlatform);
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(passwordHash)) {
			Logger.info("UserController", "login", "", "empty email or password not allowed");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User user = userRepo.findByEmail(email);
		if (user == null) {
			Logger.info("UserController", "login", "", "user not found with email = " + email);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		if (user.getPasswordHash() != null && user.getPasswordHash().equals(passwordHash)) {
			Device device = null;
			for (Device d : user.getDeviceList()) {
				if (d.getPlatform() == platform) {
					device = d;
					break;
				}
			}
			if (device == null) {
				device = new Device();
				device.setPlatform(platform);
				device.setUser(user);
				device.setDeviceId("User_" + platform.toString());
				device.setToken(DigestUtils.sha1Hex(UUID.randomUUID().toString()));
				deviceRepo.save(device);
			}
			
			String profileId = mediaController.getUserProfileId(device.getUser());
			
			HashMap<String, Serializable> userInfo = user.toParamsMap();
			userInfo.put("token", device.getToken());
			
			if (!StringUtils.isEmpty(profileId))
				userInfo.put("profileId", profileId);
			
			return new ResponseEntity<>(userInfo, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getUser", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> getUser(@RequestParam(name = "token") String token) throws AuthException {
		Device device = authenticator.validate(token);
		if (device == null || device.getUser() == null) {
			Logger.info("UserController", "getUser", "", "user not found with token = " + token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		HashMap<String, Serializable> userInfo = device.getUser().toParamsMap();
		String profileId = mediaController.getUserProfileId(device.getUser());
		if (!StringUtils.isEmpty(profileId))
			userInfo.put("profileId", profileId);
		
		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateProfile", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> updateProfile(@RequestParam(name = "token") String token,
	                                                                   @RequestParam(name = "oldPasswordHash", required = false) String oldPasswordHash,
	                                                                   @RequestParam(name = "passwordHash") String passwordHash,
	                                                                   @RequestParam(name = "name") String name,
	                                                                   @RequestParam(name = "lastName") String lastName,
	                                                                   @RequestParam(name = "profilePic", required = false) MultipartFile profilePicFile) throws AuthException {
		name = StringUtil.convertToUTF8(name);
		lastName = StringUtil.convertToUTF8(lastName);
		Device device = authenticator.validate(token);
		
		if (device == null || device.getUser() == null) {
			Logger.info("UserController", "updateProfile", "token", "device or user not found with token = " + token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		if (StringUtils.isEmpty(token) || StringUtils.isEmpty(passwordHash)) {
			Logger.info("UserController", "updateProfile", "", "empty token or password");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		User user = device.getUser();
		
		//try to save the profile pic file in the server
		Media profileMedia = mediaController.saveFile(user, FileTypeEnum.PROFILE_PICTURE_FILE, profilePicFile);
		
		//save other user information in db
		user.setName(name);
		user.setLastName(lastName);
		if (!StringUtils.isEmpty(oldPasswordHash) && oldPasswordHash.equals(user.getPasswordHash())) {
			user.setPasswordHash(passwordHash);
		} else if (!StringUtils.isEmpty(oldPasswordHash)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		user = userRepo.save(user);
		HashMap<String, Serializable> userInfo = user.toParamsMap();
		if (profileMedia != null)
			userInfo.put("profileId", profileMedia.getId());
		
		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/downloadProfile/{token}")
	public ResponseEntity<Resource> downloadProfile(@PathVariable(name = "token") String token) throws AuthException {
		Device device = authenticator.validate(token);
		if (device == null || device.getUser() == null) {
			Logger.info("UserController", "downloadProfile", "token", "device or user not found with token = " + token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		File file = mediaController.getUserProfileFile(device.getUser());
		if (file == null || !file.exists()) {
			Logger.info("UserController", "downloadProfile", "", "no profile media found for token = " + token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		try {
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			
			return ResponseEntity.ok()
					.contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(resource);
			
		} catch (IOException e) {
			e.printStackTrace();
			Logger.info("UserController", "downloadProfile", "", "internal io exception in server for token = " + token);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Scheduled(cron = "0 0 0 1 * ?")
	public void increaseCreditMonthly() {
//        List<User> users = userRepo.findByCreditLessThan(lowCredit);
//        for (User user : users) {
//            user.setCredit(lowCredit);
//        }
//        userRepo.save(users);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateFid", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> updateFid(@RequestParam(name = "token") String token,
	                                                               @RequestParam(name = "fid") String fid) throws AuthException {
		Device device = authenticator.validate(token);
		device.setFid(fid);
		deviceRepo.save(device);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateCid", produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> updateCid(@RequestParam(name = "token") String token,
	                                                               @RequestParam(name = "cid") String cid) throws AuthException {
		Device device = authenticator.validate(token);
		device.setCid(cid);
		deviceRepo.save(device);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/invite", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ResponseEntity<HashMap<String, Serializable>> invite(@RequestParam(name = "token") String token,
	                                                            @RequestParam(name = "emails") String[] emails)
			throws AuthException {
		Device device = authenticator.validate(token);
		for (String email : emails) {
			mailUtil.sendMail(email, Spring.context.getMessage("inviteSubject", null, Locale.forLanguageTag("en")),
					Spring.context.getMessage("inviteMail", new Object[]{device.getUser().getEmail(),
							device.getUser().getId()}, Locale.forLanguageTag("en")),
					"", null);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private String generateCode() {
		//return "12345";
		return String.valueOf(random.nextInt(10)) +
				String.valueOf(random.nextInt(10)) +
				String.valueOf(random.nextInt(10)) +
				String.valueOf(random.nextInt(10)) +
				String.valueOf(random.nextInt(10));
	}
}
