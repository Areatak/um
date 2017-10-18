package com.areatak.gazette.controller;

import com.areatak.gazette.dao.CoinPaymentRepo;
import com.areatak.gazette.dao.DeviceRepo;
import com.areatak.gazette.dao.PaymentRepo;
import com.areatak.gazette.dao.UserRepo;
import com.areatak.gazette.entities.CoinPayment;
import com.areatak.gazette.entities.Device;
import com.areatak.gazette.exception.AuthException;
import com.areatak.gazette.metadata.CoinType;
import com.areatak.gazette.security.Authenticator;
import com.areatak.util.CoinUtils;
import com.areatak.util.QRUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Base64;
import java.util.HashMap;

/**
 * Created by alirezaghias on 7/4/2017 AD.
 */
@RestController
public class CoinPaymentController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private QRUtil qrUtil;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private DeviceRepo deviceRepo;
    @Autowired
    private MediaController mediaController;
    @Autowired
    private Authenticator authenticator;
    @Autowired
    private CoinPaymentRepo coinPaymentRepo;

    @RequestMapping(method = RequestMethod.POST, value = "/buyCreditWithCoin")
    public ResponseEntity<HashMap<String, Serializable>> buyCreditWithCoin(@RequestParam(name = "token") String token,
                                                                           @RequestParam CoinType coinType,
                                                                           @RequestParam(required = false) String stomp,
                                                                           @RequestParam(name = "amount") double amount) throws AuthException {

        Device device = authenticator.validate(token);

        String coinAddress = CoinUtils.getBitcoinConnection().getNewAddress();
        String address = CoinUtils.generateCoinPaymentUrl(coinType, amount, coinAddress);
        CoinPayment payment = new CoinPayment();
        payment.setUser(device.getUser());
        payment.setStompId(stomp);
        payment.setStatus(CoinPayment.Status.Listening);
        payment.setCoinType(CoinType.Utabit);
        payment.setAddress(coinAddress);
        payment.setAmount(amount);
        coinPaymentRepo.save(payment);
        byte[] qr = qrUtil.generate(address, 150);
        String qrString = Base64.getEncoder().encodeToString(qr);
        HashMap<String, Serializable> result = payment.toParamsMap();
        result.put("url", address);
        result.put("qr", qrString);
        return new ResponseEntity<HashMap<String, Serializable>>(result, HttpStatus.OK);

    }
}
