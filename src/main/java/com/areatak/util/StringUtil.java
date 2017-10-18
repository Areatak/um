package com.areatak.util;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.lang3.CharEncoding;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by alirezaghias on 5/9/2017 AD.
 */
public class StringUtil {
    public static String convertToUTF8(String msg) {
        if (msg != null && Charset.forName(CharEncoding.ISO_8859_1).newEncoder().canEncode(msg)) {
            try {
                return new String(msg.getBytes("ISO_8859_1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }
    public static String capitalize(String word)
    {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
    public static String randomString(int characterLength){
        return UUID.randomUUID().toString().substring(0,characterLength);
    }

    public static String addSeparator(String s) {
        if (s == null) return "";
        StringBuilder f = new StringBuilder();
        String temp = s.replaceAll(",", "");
        for (int i = 0; i < temp.length(); ++i) {
            f.append(temp.charAt(i));
            if (((temp.length() - 1) - i) % 3 == 0 && ((temp.length() - 1) - i) != 0) {
                f.append(",");
            }
        }
        return f.toString();
    }
    public static String persianFix(String text) {
        if (StringUtils.isEmpty(text)) {
            return "";
        } else {
            return text.replaceAll("\u06AA", "ک").replaceAll("\u0643", "ک").replaceAll("\u0649", "ی").replaceAll("\u064A", "ی");
        }
    }
}
