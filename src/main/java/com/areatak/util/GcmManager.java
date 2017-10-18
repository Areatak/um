package com.areatak.util;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Ashki on 11/30/2015.
 */
@Component
@Scope("singleton")
public class GcmManager {
    @Value("${gcm_api_key}")
    private String API_KEY;
    private String GCM_URL = "https://fcm.googleapis.com/fcm/send";
    private ExecutorService executorService= Executors.newSingleThreadExecutor();
    public void sendDataTo(String token) {
        sendTo(null, ImmutableMap.of(), token);
    }
    public void sendNotificationTo(String title, String body, String json, String token) {
        sendTo(ImmutableMap.of("body", body, "title", title), ImmutableMap.of("json", json), token);

    }


    private  void sendTo(Map<String, String> notification, Map<String, Object> data, String token) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL obj = new URL(GCM_URL);
                    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                    addAuthToken(con);
                    Map<String, Object> param = new LinkedHashMap<>();
                    if (notification != null) {
                        param.put("notification", notification);
                    }
                    param.put("data", data);
                    param.put("to", token);
                    param.put("content_available", true);
                    param.put("priority", "high");
//                    param.put("collapse_key", "updateApp");

                    ConnectionService.addPostParameter(con, param);
                    final String response = ConnectionService.getResponse(con);
                    con.disconnect();
//                    log.info("GCM receive:" + response);
                } catch (Exception e){
                    Logger.error("GcmManager", "sendTo", "GCM", e.getMessage(), e);
                }
            }
        });

    }


    private void addAuthToken(HttpURLConnection con) {
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", " key=" + API_KEY);
    }

}
