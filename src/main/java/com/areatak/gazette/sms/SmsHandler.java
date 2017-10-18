package com.areatak.gazette.sms;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Cross on 4/14/2015.
 */
@Component
@Scope(value = "singleton")
/**
 *
 */
public class SmsHandler {
    private static Properties config;
    private static final Logger logger = Logger.getLogger(SmsHandler.class);

    static {
        try {
            config = new Properties();
            config.load(SmsHandler.class.getClassLoader().getResourceAsStream("sms.properties"));
        } catch (IOException e) {
            logger.error("Could not find sms.properties in classpath " + e.getMessage());
        }
    }

    private SmsHandler() {
    }

    private final List<SmsListener> listeners = new LinkedList<>();

    /**
     * @param smsListener
     */
    public void addSmsListener(SmsListener smsListener) {
        listeners.add(smsListener);
    }

    /**
     * @param smsListener
     */
    public void setSmsListener(SmsListener smsListener) {
        listeners.clear();
        listeners.add(smsListener);
    }

    /**
     * @param from
     * @param to
     * @param text
     * @param udh
     */
    public void process(String from, String to, String text, String udh) {
        for (final SmsListener l : listeners)
            l.processSms(from, to, text, udh);
    }
    public void sendSms(String to, String text) {
        try {
            doSendSms(to, text, config.getProperty("username"), config.getProperty("password"));
        } catch (ServiceException | RemoteException | MalformedURLException e) {
            logger.fatal(e.getMessage(), e);
        }
    }
    public void doSendSms(String to, String text, String username, String password) throws ServiceException, MalformedURLException, RemoteException {
        logger.info("sending sms to " + to + " message " + text);
        String END_POINT_URL = config.getProperty("server");
        String URN = "urn:SOAPSmsQueue";
        String ENQUEUE_METHOD_CALL = "enqueue";
        String USER_NAME = username; //fill this with your username
        String PASSWORD = password;  //fill this with your password
        String SENDER_NUMBER = config.getProperty("smsNO"); //your 3000xxxxx number
        String RECIPIENT_NUMBER = to; //the phone number you wish to send something to...
        String DOMAIN = "areatak";    //fill this with your domain


        //creating a service object
        Service service = new Service();
        //creating a call object from the service and setting it's basic properties
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new URL(END_POINT_URL));
        call.setOperationName(new QName(URN, ENQUEUE_METHOD_CALL));
        call.setUsername(USER_NAME);
        call.setPassword(PASSWORD);
        call.setReturnType(XMLType.SOAP_ARRAY);
        call.setTimeout(10 * 1000);
        //defining the parameters the service accepts
        call.addParameter("domain", XMLType.SOAP_STRING, ParameterMode.IN);
        call.addParameter("messages", XMLType.SOAP_ARRAY, ParameterMode.IN);
        call.addParameter("recipientNumbers", XMLType.SOAP_ARRAY, ParameterMode.IN);
        call.addParameter("senderNumbers", XMLType.SOAP_ARRAY, ParameterMode.IN);
        call.addParameter("encodings", XMLType.SOAP_ARRAY, ParameterMode.IN);
        call.addParameter("udhs", XMLType.SOAP_ARRAY, ParameterMode.IN);
        call.addParameter("messageClasses", XMLType.SOAP_ARRAY, ParameterMode.IN);
        call.addParameter("priorities", XMLType.SOAP_ARRAY, ParameterMode.IN);
        call.addParameter("checkingMessageIds", XMLType.SOAP_ARRAY, ParameterMode.IN);

        String domain;
        String[] messages;
        String[] recipientNumbers;
        String[] senderNumbers;
        int[] encodings;
        String[] udhs;
        Integer[] mClass;
        Integer[] priorities;
        long[] checkingMessageIds;

        domain = DOMAIN;
        messages = new String[1];
        recipientNumbers = new String[1];
        senderNumbers = new String[1];
        encodings = new int[1];
        udhs = null;
        mClass = null;
        priorities = null;
        checkingMessageIds = new long[1];

        for (int i = 0; i < 1; i++) {
            recipientNumbers[i] = RECIPIENT_NUMBER;
            senderNumbers[i] = SENDER_NUMBER;
            checkingMessageIds[i] = i + 10L;
            messages[i] = text;
            encodings[i] = -1;
        }
        //preparing the inputs for calling the service
        Object[] params = {domain, messages, recipientNumbers, senderNumbers, encodings, udhs, mClass, priorities, checkingMessageIds};
        //preparing the object array to be filled as output values
        Object[] returnArray = null;


        double startTime = System.currentTimeMillis();
        //making the request
        returnArray = (Object[]) call.invoke(params);
        System.out.println("request response-time=" + (System.currentTimeMillis() - startTime) / 1000 + " sec.s");

        //print out the results
        if (returnArray != null) {
            for (int i = 0; i < returnArray.length; i++) {
                final long returnValue = (Long) returnArray[i];
                if (returnValue < ErrorCodes.WEB_SENDER_NUMBER_ARRAY_IS_NULL.getCode())
                    System.out.println("caught error: " + returnValue + ", " + ErrorCodes.getDescriptionForCode((int) returnValue));
                else
                    System.out.println("returnArray index " + i + " = " + returnValue);
            }
        }

//            final String url = config.getProperty("server") + "?" + "from=" + config.getProperty("smsNO") + "&to=" + to + "&message=" + URLEncoder.encode(text, "UTF-8") + "&username=" + config.getProperty("username") + "&password=" + config.getProperty("password") + "&user_login=" + config.getProperty("user_login");
//            final String url = makeUrlString(to, text);
//            try {
//                final URL obj = new URL(url);
//                logger.info("url = " + url);
//                final HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//                final int responseCode = con.getResponseCode();
//
//                logger.info("Sms response=" + ConnectionService.getResponse(con) + " sent to:" + to + " text:" + text);
//            } catch (MalformedURLException e) {
//                logger.error("Malformed Url=" + url);
//            } catch (IOException e) {
//                logger.error("IO Exception could not open connection Url=" + url);
//            }
    }

    private static String makeUrlString(String to, String text) throws UnsupportedEncodingException {
        String END_POINT_URL = config.getProperty("server");
        String METHOD_CALL = "enqueue";

        String USER_NAME = config.getProperty("username"); //fill this with your username
        String PASSWORD = config.getProperty("password");  //fill this with your password
        String SENDER_NUMBER = config.getProperty("smsNO"); //your 3000xxxxx number
        String RECIPIENT_NUMBER = to; //the phone number you wish to send something to...
        String DOMAIN = "areatak";    //fill this with your domain

        String MESSGAE_TEXT = text;
        String UDH = "";       //can be left blank
        String ENCODING = "";  //encoding of the message. if left blank, system will guess the message encoding automatically
        String CHECKING_MESSAGE_ID = "";   //can be left blank

        final StringBuilder sb = new StringBuilder(END_POINT_URL);
        sb.append("service=").append(METHOD_CALL).append("&");
        sb.append("username=").append(USER_NAME).append("&");

        sb.append("password=").append(URLEncoder.encode(PASSWORD, "UTF-8")).append("&");
        sb.append("from=").append(SENDER_NUMBER).append("&");
        sb.append("to=").append(RECIPIENT_NUMBER).append("&");
        sb.append("domain=").append(DOMAIN).append("&");
        sb.append("message=").append(URLEncoder.encode(text, "UTF-8")).append("&");
        sb.append("udh=").append(UDH).append("&");
        sb.append("coding=").append(ENCODING).append("&");
        sb.append("chkmessageid=").append(CHECKING_MESSAGE_ID);

        return sb.toString();
    }
}
