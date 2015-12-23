/*
 * Copyright madding.me.
 */
package com.madding.shared.encrypt.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.madding.shared.encrypt.ProviderUtil;
import com.madding.shared.encrypt.cert.bc.constant.MadBCConstant;
import com.madding.shared.encrypt.util.Hex;

/**
 * 类Md5.java的实现描述：TODO 类实现描述
 * 
 * query OID: http://www.oid-info.com/get/2.16.840.1.101.3.4.2.6
 * 
 * @author madding.lip May 8, 2012 1:59:09 PM
 */
public class MD {

    public static final String DEFAULT_CHARSET = "UTF-8";
    
    static {
        ProviderUtil.addBCProvider();
    }

    public static String md2(String src) {
        try {
            return md2(src.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String md2(byte[] src) {
        try {
//            MessageDigest messageDigest = MessageDigest.getInstance("MD2");
            MessageDigest messageDigest = MessageDigest.getInstance("1.2.840.113549.2.2", MadBCConstant.JCE_PROVIDER);
            messageDigest.update(src);
            return new String(Hex.encodeHex(messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String md4(String src) {
        try {
            return md4(src.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String md4(byte[] src) {
        try {
//            MessageDigest messageDigest = MessageDigest.getInstance("MD4"); 
            MessageDigest messageDigest = MessageDigest.getInstance("1.2.840.113549.2.4", MadBCConstant.JCE_PROVIDER);
            messageDigest.update(src);
            return new String(Hex.encodeHex(messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String md5(String src) {
        try {
            return md5(src.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String md5(byte[] src) {
        try {
//            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            MessageDigest messageDigest = MessageDigest.getInstance("1.2.840.113549.2.5", MadBCConstant.JCE_PROVIDER);
            
            messageDigest.update(src);
            return new String(Hex.encodeHex(messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "";
    }

}
