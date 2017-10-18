package com.areatak.util;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by Ashki on 11/30/2015.
 */
public class ConnectionService {


    public static void addPostParameter(HttpURLConnection con, Map<String, Object> map) {
        try {
            con.setRequestMethod("POST");
            byte[] postData = new Gson().toJson(map).getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
                wr.flush();
                wr.close();
            } catch (IOException e) {
                Logger.error("ConnectionService", "addPostParameter", "GCM", e.getMessage(), e);
            }

        } catch (ProtocolException e) {
            Logger.error("ConnectionService", "addPostParameter", "GCM", e.getMessage(), e);
        }

    }

    public static String getResponse(HttpURLConnection con) {
        try {
            con.connect();
            return new String(getResponseBytes(con));
        } catch (Exception e) {
            Logger.error("ConnectionService", "getResponse", "GCM", e.getMessage(), e);
        }
//        log.info("GCM ERR!!");
        return "";
    }

    public static byte[] getResponseBytes(URLConnection url) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = url.getInputStream();
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;

            while ( (n = is.read(byteChunk)) > 0 ) {
                baos.write(byteChunk, 0, n);
            }
            return baos.toByteArray();
        }
        catch (IOException e) {
            Logger.error("ConnectionService", "getResponse", "GCM", e.getMessage(), e);
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
