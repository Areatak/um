package com.areatak.util;

/**
 * Created by alirezaghias on 7/16/16.
 */
public class PhoneUtil {
    public static String toPlain(String phone) {
        if (phone != null)
            return phone.replace("+98", "0").replace("+", "00").replaceAll("[^0-9]+", "");
        return "";
    }
    public static String toHide(String phone) {
        if(phone == null)
            phone ="";
        if (phone.length() > 7)
//            return phone;
            return "\u200E" + phone.substring(0, 7) + "****" + "\u200E";
        return phone;
    }


}
