/*
 * Copyright madding.me.
 */
package com.madding.shared.encrypt.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 类SHA.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip May 8, 2012 2:01:34 PM
 */
public class SHA {

    public static final String  ALGORITHM       = "SHA";
    public static final String  DEFAULT_CHARSET = "UTF-8";

    private static final char[] DIGITS          = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
            'd', 'e', 'f'                      };

    public final static String sha(String source) {
        try {
            return sha(source.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public final static String sha(byte[] src) {
        try {
            MessageDigest alga = MessageDigest.getInstance(ALGORITHM);
            alga.update(src);
            return new String(encodeHex(alga.digest()));
        } catch (Exception e) {
            return null;
        }
    }

    protected final static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

}
