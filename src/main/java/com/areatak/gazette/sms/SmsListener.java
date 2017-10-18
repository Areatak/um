package com.areatak.gazette.sms;

/**
 * Created by Cross on 4/14/2015.
 */

/**
 *
 */
public interface SmsListener {
    /**
     *
     * @param from
     * @param to
     * @param text
     * @param udh
     */
    void processSms(String from, String to, String text, String udh);

}
