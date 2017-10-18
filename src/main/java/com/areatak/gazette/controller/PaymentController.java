package com.areatak.gazette.controller;

import com.areatak.gazette.dao.DeviceRepo;
import com.areatak.gazette.dao.PaymentRepo;
import com.areatak.gazette.dao.UserRepo;
import com.areatak.gazette.entities.Device;
import com.areatak.gazette.entities.Media;
import com.areatak.gazette.entities.Payment;
import com.areatak.gazette.entities.User;
import com.areatak.gazette.exception.AuthException;
import com.areatak.gazette.metadata.Platform;
import com.areatak.gazette.security.Authenticator;
import com.areatak.util.*;
import com.google.gson.Gson;
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
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zarrinpal.PaymentGatewayImplementationService;
import zarrinpal.PaymentGatewayImplementationServicePortType;
import zarrinpal.PaymentRequest;

import javax.xml.ws.Holder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Farnoosh on 1396/02/06.
 */
@Controller
public class PaymentController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private DeviceRepo deviceRepo;
    @Autowired
    private MediaController mediaController;
    @Autowired
    private Authenticator authenticator;
    @Value("${merchantID}")
    private String merchantId;

    @RequestMapping(method = RequestMethod.GET, value = "/buyCredit")
    public String buyCredit(@RequestParam(name = "token") String token,
                            @RequestParam(name = "ubit") double ubit, RedirectAttributes redirectAttributes) throws AuthException {

        Device device = authenticator.validate(token);


        int amount = (int) Math.ceil(ubit * TransactionController.getCurrencyRate());

        PaymentGatewayImplementationServicePortType service = new PaymentGatewayImplementationService().getPaymentGatewayImplementationServicePort();
        Holder<Integer> status = new Holder<>();
        Holder<String> authority = new Holder<>();
        Payment payment = new Payment();
        payment.setUbit(ubit);
        payment.setUser(device.getUser());
        payment.setDescription(Spring.context.getMessage("paymentDesc", new Object[]{String.format("%.2f", ubit)}, Locale.forLanguageTag("en")));
        payment.setAmount(amount);
        if (device.getPlatform() == Platform.IOS || device.getPlatform() == Platform.ANDROID)
            payment.setGateType(Payment.ZarinGateType.Mobile);
        else
            payment.setGateType(Payment.ZarinGateType.Web);
        payment.setStatus(Payment.PaymentStatus.Pending);
        paymentRepo.save(payment);
        service.paymentRequest(merchantId,
                payment.getAmount(),
                payment.getDescription(),
                "",
                "",
                "http://utadoc.com/verifyCredit?id=" + payment.getId(),
                status,
                authority);
        payment.setAuthority(authority.value);
        payment.setZarinStatus(status.value);
        if (status.value == 100 && authority.value.length() == 36) {
            paymentRepo.save(payment);
            String gate = "";
            if (payment.getGateType() == Payment.ZarinGateType.Mobile) {
                gate = "/MobileGate";
            } else if (payment.getGateType() == Payment.ZarinGateType.Zarin) {
                gate = "/ZarinGate";
            } else {
                gate = "";
            }
            return "redirect:https://www.zarinpal.com/pg/StartPay/" + payment.getAuthority() + gate;
        } else {
            payment.setStatus(Payment.PaymentStatus.Error);
            paymentRepo.save(payment);
        }
        redirectAttributes.addFlashAttribute("paymentMode", true);
        redirectAttributes.addFlashAttribute("status", payment.getZarinStatus());
        redirectAttributes.addFlashAttribute("statusString", payment.getZarinStatusString());
        return "redirect:/";

    }

    @RequestMapping(method = RequestMethod.GET, value = "/verifyCredit")
    public String buyCredit(@RequestParam(name = "id") String paymentId, RedirectAttributes redirectAttributes) throws AuthException {
        Payment payment = paymentRepo.findOne(paymentId);
        if (payment != null) {
            PaymentGatewayImplementationServicePortType service = new PaymentGatewayImplementationService().getPaymentGatewayImplementationServicePort();
            Holder<Integer> status = new Holder<>();
            Holder<Long> refId = new Holder<>();
            service.paymentVerification(merchantId, payment.getAuthority(), payment.getAmount(), status, refId);
            payment.setZarinStatus(status.value);
            payment.setRefId(refId.value);
            redirectAttributes.addFlashAttribute("paymentMode", true);
            redirectAttributes.addFlashAttribute("status", payment.getZarinStatus());
            redirectAttributes.addFlashAttribute("statusString", payment.getZarinStatusString());
            redirectAttributes.addFlashAttribute("refId", payment.getRefId());
            if (payment.getStatus() != Payment.PaymentStatus.Paid && payment.getStatus() != Payment.PaymentStatus.Success) {
                if (status.value == 100 || status.value == 101) {
                    payment.setStatus(Payment.PaymentStatus.Paid);
                    payment = paymentRepo.save(payment);
                }
            }
            if (status.value == 100 || (status.value == 101 && payment.getStatus() != Payment.PaymentStatus.Success)) {
                addPaymentToUser(payment);
            } else {
                if (payment.getStatus() != Payment.PaymentStatus.Paid && payment.getStatus() != Payment.PaymentStatus.Success) {
                    payment.setStatus(Payment.PaymentStatus.Error);
                    paymentRepo.save(payment);
                }
            }
            return "redirect:/";
        } else {
            return "404";
        }
    }

    @Transactional
    private void addPaymentToUser(Payment payment) {
        User user = payment.getUser();
//        user.setCredit(user.getCredit() + payment.getUbit());
        userRepo.save(user);
        payment.setStatus(Payment.PaymentStatus.Success);
        paymentRepo.save(payment);
    }
}
