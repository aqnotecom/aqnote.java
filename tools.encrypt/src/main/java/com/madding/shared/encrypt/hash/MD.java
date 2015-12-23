/*
 * Copyright madding.me.
 */
package com.madding.shared.encrypt.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import com.madding.shared.encrypt.util.Hex;

/**
 * 类Md5.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip May 8, 2012 1:59:09 PM
 */
public class MD {

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static String md5(byte[] src) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(src);
            return new String(Hex.encodeHex(messageDigest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String md5(String text) {
        return md5(text, DEFAULT_CHARSET);
    }

    public static String md5(String str, String charset) {
        try {
            return md5(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
