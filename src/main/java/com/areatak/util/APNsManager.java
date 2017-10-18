package com.areatak.util;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alirezaghias on 8/20/2016 AD.
 */
@Component
@Scope("singleton")
public class APNsManager {
    @Value("${apns_certificate_password}")
    private String password;
    private ApnsService apnsClient_Dev;
    private ApnsService apnsClient_Prod;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void setup() {
        if (apnsClient_Dev == null) {
            apnsClient_Dev = APNS.newService().withCert(APNsManager.class.getClassLoader().getResourceAsStream("APNS_Sabtshod_Dev.p12"), password).withAppleDestination(false).build();
        }
        if (apnsClient_Prod == null) {
            apnsClient_Prod = APNS.newService().withCert(APNsManager.class.getClassLoader().getResourceAsStream("APNS_Sabtshod_Prod.p12"), password).withAppleDestination(true).build();
        }
    }
    private void push(String cid, String payload) {
        try {
            apnsClient_Prod.push(cid, payload);
        } catch (Exception ex) {
            Logger.error("APNS", "push", "APNS", ex.getMessage(), ex);
        }
        try {
            apnsClient_Dev.push(cid, payload);
        } catch (Exception ex) {
            Logger.error("APNS", "push", "APNS", ex.getMessage(), ex);
        }


    }
    public void sendDataTo(String json, String cid) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                setup();
                String payload = APNS.newPayload().customField("json", json).instantDeliveryOrSilentNotification().build();
                push(cid, payload);
            }
        });
    }



    public void sendNotificationTo(String title, String body, String json, String cid) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                setup();
                String payload = APNS.newPayload().alertTitle(title).alertBody(body).customField("json", json).build();
                push(cid, payload);


            }
        });
    }
}
