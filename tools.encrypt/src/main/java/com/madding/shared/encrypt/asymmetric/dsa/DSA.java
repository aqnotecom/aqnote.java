/*
 * Copyright madding.me.
 */
package com.madding.shared.encrypt.asymmetric.dsa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类DSA.java的实现描述：数字签名/检验的工具
 * 
 * @author madding.lip May 7, 2012 3:38:19 PM
 */
public class DSA {

    private static final Logger       log                = LoggerFactory.getLogger(DSA.class);

    public static final String        ALGORITHM          = "DSA";

    /** 将base64中的大写字母escape的char。 */
    public static final char          BASE64_ESCAPE_CHAR = '_';

    private Map<String, KeyPairEntry> keyPairs           = new HashMap<String, KeyPairEntry>();

    public PublicKey getPublicKey(String name) {
        KeyPairEntry entry = getEntry(name, false);
        if (entry == null) {
            return null;
        }
        return entry.publicKey;
    }

    public void setPublicKey(String name, InputStream istream) throws IOException, RuntimeException {
        setPublicKey(name, getBytes(istream));
    }

    public void setPublicKey(String name, byte[] keyBytes) throws RuntimeException {
        KeyPairEntry entry = getEntry(name, true);
        if (entry.publicKey != null) {
            throw new IllegalArgumentException("duplicated public key for name: " + name);
        }
        entry.publicKey = readPublicKey(keyBytes);
    }

    public PrivateKey getPrivateKey(String name) {
        KeyPairEntry entry = getEntry(name, false);
        if (entry == null) {
            return null;
        }
        return entry.privateKey;
    }

    public void setPrivateKey(String name, InputStream istream) throws IOException, RuntimeException {
        setPrivateKey(name, getBytes(istream));
    }

    public void setPrivateKey(String name, byte[] keyBytes) throws RuntimeException {
        KeyPairEntry entry = getEntry(name, true);
        if (entry.privateKey != null) {
            throw new IllegalArgumentException("duplicated private key for name: " + name);
        }
        entry.privateKey = readPrivateKey(keyBytes);
    }

    private KeyPairEntry getEntry(String name, boolean create) {
        KeyPairEntry entry = (KeyPairEntry) keyPairs.get(name);
        if ((entry == null) && create) {
            entry = new KeyPairEntry();
            keyPairs.put(name, entry);
        }
        return entry;
    }

    private byte[] getBytes(InputStream istream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int amount;
        byte[] buffer = new byte[8192];
        while ((amount = istream.read(buffer)) >= 0) {
            baos.write(buffer, 0, amount);
        }
        return baos.toByteArray();
    }

    /**
     * 从字节串中读取public key。
     * 
     * @throws RuntimeException 创建key失败
     */
    public static PublicKey readPublicKey(byte[] keyBytes) throws RuntimeException {
        return (PublicKey) readKey(keyBytes, true, true);
    }

    /**
     * 从字节串中读取private key。
     * 
     * @throws RuntimeException 创建key失败
     */
    public static PrivateKey readPrivateKey(byte[] keyBytes) throws RuntimeException {
        return (PrivateKey) readKey(keyBytes, false, true);
    }

    /**
     * 从字节串中读取public/private key。
     * 
     * @throws RuntimeException 创建key失败
     */
    private static Key readKey(byte[] keyBytes, boolean isPublicKey, boolean base64Encoded) throws RuntimeException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            byte[] encodedKey = keyBytes;

            // 先base64解码
            if (base64Encoded) {
                encodedKey = Base64.decodeBase64(encodedKey);
            }

            if (isPublicKey) {
                EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
                return keyFactory.generatePublic(keySpec);
            } else {
                EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
                return keyFactory.generatePrivate(keySpec);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to create key", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Failed to create key", e);
        }
    }

    /**
     * 对指定字符串进行签名。
     * 
     * @param content 要签名的字符串
     * @param keyPairName key pair
     * @return base64编码的签名
     */
    public String sign(String content, String keyPairName) throws RuntimeException {
        return sign(getDataToSign(content, null), keyPairName);
    }

    /**
     * 对指定字符串进行签名。
     * 
     * @param content 要签名的字符串
     * @param keyPairName key pair
     * @param charset 字符串的编码字符集
     * @return base64编码的签名
     */
    public String sign(String content, String keyPairName, String charset) throws RuntimeException {
        return sign(getDataToSign(content, charset), keyPairName);
    }

    /**
     * 对指定字符串进行签名。
     * 
     * @param content 要签名的字符串
     * @param keyPairName key pair
     * @return base64编码的签名
     */
    public String sign(Map<?, ?> content, String keyPairName) throws RuntimeException {
        return sign(getDataToSign(content, null), keyPairName);
    }

    /**
     * 对指定字符串进行签名。
     * 
     * @param content 要签名的字符串
     * @param keyPairName key pair
     * @param charset 字符串的编码字符集
     * @return base64编码的签名
     */
    public String sign(Map<?, ?> content, String keyPairName, String charset) throws RuntimeException {
        return sign(getDataToSign(content, charset), keyPairName);
    }

    /**
     * 对指定字节流进行签名。
     * 
     * @param content 要签名的字节流
     * @param keyPairName key pair
     * @return base64编码的签名
     */
    public String sign(byte[] content, String keyPairName) throws RuntimeException {
        try {
            return sign((Object) content, keyPairName);
        } catch (IOException e) {
            throw new RuntimeException(e); // 不会发生此异常
        }
    }

    /**
     * 对指定输入流进行签名。
     * 
     * @param content 要签名的输入流
     * @param keyPairName key pair
     * @return base64编码的签名
     */
    public String sign(InputStream content, String keyPairName) throws RuntimeException, IOException {
        return sign((Object) content, keyPairName);
    }

    private String sign(Object content, String keyPairName) throws RuntimeException, IOException {
        KeyPairEntry entry = (KeyPairEntry) keyPairs.get(keyPairName);

        if (entry == null) {
            throw new RuntimeException(keyPairName);
        }

        PrivateKey prikey = entry.privateKey;

        if (prikey == null) {
            throw new RuntimeException("missing private key in key pair: " + keyPairName);
        }

        try {
            Signature signature = Signature.getInstance(ALGORITHM);

            signature.initSign(prikey);

            if (content instanceof byte[]) {
                signature.update((byte[]) content);
            } else {
                updateSignature((InputStream) content, signature);
            }

            byte[] signed = signature.sign();
            byte[] signedRS = signatureToRS(signed);

            if (log.isDebugEnabled()) {
                log.debug("Java signature[length=" + signed.length + "]: " + dumpBytes(signed));
                log.debug("  in RS format[length=" + signedRS.length + "]: " + dumpBytes(signedRS));
            }

            byte[] bf = Base64.encodeBase64(signedRS, false);

            return encodeUpperCase(bf);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Could not sign content", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not sign content", e);
        } catch (SignatureException e) {
            throw new RuntimeException("Could not sign content", e);
        }
    }

    /**
     * 检验content的签名。
     * 
     * @param content 要检验的内容
     * @param signature 签名
     * @param keyPairName key pair
     * @return 如果签名正确，则返回<code>true</code>
     */
    public boolean check(String content, String signature, String keyPairName) throws RuntimeException {
        return check(getDataToSign(content, null), signature, keyPairName);
    }

    /**
     * 检验content的签名。
     * 
     * @param content 要检验的内容
     * @param signature 签名
     * @param keyPairName key pair
     * @param 字符串的编码字符集
     * @return 如果签名正确，则返回<code>true</code>
     */
    public boolean check(String content, String signature, String keyPairName, String charset) throws RuntimeException {
        return check(getDataToSign(content, charset), signature, keyPairName);
    }

    /**
     * 检验content的签名。
     * 
     * @param content 要检验的内容
     * @param signature 签名
     * @param keyPairName key pair
     * @return 如果签名正确，则返回<code>true</code>
     */
    public boolean check(Map<?, ?> content, String signature, String keyPairName) throws RuntimeException {
        return check(getDataToSign(content, null), signature, keyPairName);
    }

    /**
     * 检验content的签名。
     * 
     * @param content 要检验的内容
     * @param signature 签名
     * @param keyPairName key pair
     * @param 字符串的编码字符集
     * @return 如果签名正确，则返回<code>true</code>
     */
    public boolean check(Map<?, ?> content, String signature, String keyPairName, String charset) throws RuntimeException {
        return check(getDataToSign(content, charset), signature, keyPairName);
    }

    /**
     * 检验content的签名。
     * 
     * @param content 要检验的内容
     * @param signature 签名
     * @param keyPairName key pair
     * @return 如果签名正确，则返回<code>true</code>
     */
    public boolean check(byte[] content, String signature, String keyPairName) throws RuntimeException {
        try {
            return check((Object) content, signature, keyPairName);
        } catch (IOException e) {
            throw new RuntimeException(e); // 不会发生此异常
        }
    }

    /**
     * 检验content的签名。
     * 
     * @param content 要检验的内容
     * @param signature 签名
     * @param keyPairName key pair
     * @return 如果签名正确，则返回<code>true</code>
     */
    public boolean check(InputStream content, String signature, String keyPairName) throws RuntimeException,
                                                                                   IOException {
        return check((Object) content, signature, keyPairName);
    }

    private boolean check(Object content, String signature, String keyPairName) throws RuntimeException, IOException {
        KeyPairEntry entry = (KeyPairEntry) keyPairs.get(keyPairName);

        if (entry == null) {
            throw new RuntimeException(keyPairName);
        }

        PublicKey pubkey = entry.publicKey;

        if (pubkey == null) {
            throw new RuntimeException("missing public key in key pair: " + keyPairName);
        }

        try {
            byte[] signedRS = Base64.decodeBase64(decodeUpperCase(signature));
            byte[] signed = rsToSignature(signedRS);

            if (log.isDebugEnabled()) {
                log.debug("Java signature[length=" + signed.length + "]: " + dumpBytes(signed));
                log.debug("  in RS format[length=" + signedRS.length + "]: " + dumpBytes(signedRS));
            }

            Signature signCheck = Signature.getInstance(ALGORITHM);

            signCheck.initVerify(pubkey);

            if (content instanceof byte[]) {
                signCheck.update((byte[]) content);
            } else {
                updateSignature((InputStream) content, signCheck);
            }

            return signCheck.verify(signed);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Could not check content", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not check content", e);
        } catch (SignatureException e) {
            throw new RuntimeException("Could not check content", e);
        }
    }

    /**
     * 将输入流中的内容更新到signature中。
     */
    private void updateSignature(InputStream in, Signature signature) throws IOException, SignatureException {
        int count;
        byte[] buffer = new byte[8192];

        while ((count = in.read(buffer)) >= 0) {
            signature.update(buffer, 0, count);
        }
    }

    /**
     * 取得指定content的字节串。
     * 
     * @throws RuntimeException
     */
    private byte[] getDataToSign(String content, String charset) throws RuntimeException {
        try {
            if (charset == null || "".equals(charset)) {
                charset = new OutputStreamWriter(new ByteArrayOutputStream()).getEncoding();
                charset = Charset.forName(charset).name();
            }
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Invalid charset: " + charset, e);
        }
    }

    /**
     * 取得指定content的字节串。
     * 
     * @throws RuntimeException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private byte[] getDataToSign(Map<?, ?> content, String charset) throws RuntimeException {
        List keys = new ArrayList(content.keySet());
        Collections.sort(keys);
        StringBuffer buffer = new StringBuffer();
        for (Iterator<?> i = keys.iterator(); i.hasNext();) {
            String key = (String) i.next();
            String value = (String) content.get(key);
            value =(value == null) ? "" : value;
            buffer.append(key).append(value);
        }

        return getDataToSign(buffer.toString(), charset);
    }

    /**
     * 将大写字母转换成"_"+小写字母。
     */
    private String encodeUpperCase(byte[] bytes) {
        StringBuffer buffer = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            char ch = (char) bytes[i];

            if (Character.isUpperCase(ch)) {
                buffer.append(BASE64_ESCAPE_CHAR).append(Character.toLowerCase(ch));
            } else {
                buffer.append(ch);
            }
        }

        return buffer.toString();
    }

    /**
     * 将"_"+小写字母转换成大写字母。
     */
    private byte[] decodeUpperCase(String str) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(str.length());

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if ((ch == BASE64_ESCAPE_CHAR) && (i < (str.length() - 1))) {
                char nextChar = Character.toUpperCase(str.charAt(++i));

                baos.write((int) nextChar);
            } else {
                baos.write((int) ch);
            }

        }

        try {
            baos.close();
        } catch (IOException e) {
        }

        return baos.toByteArray();
    }

    /**
     * 将字节显示出来。
     */
    private String dumpBytes(byte[] bytes) {
        StringBuffer buffer = new StringBuffer(bytes.length * 3);

        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;
            String hex = Integer.toHexString(value).toUpperCase();

            if (hex.length() < 2) {
                buffer.append('0');
            }

            buffer.append(hex).append(' ');
        }

        return buffer.toString();
    }

    private static final int START_TAG = 0x30;
    private static final int SEP_TAG   = 0x02;

    /**
     * 将Sun provider提供的signature的格式转换为固定的40字节长的格式。
     */
    public static byte[] signatureToRS(byte[] signature) throws RuntimeException {
        if ((signature == null) || (signature[0] != START_TAG)) {
            throw new RuntimeException("Invalid signature format");
        }

        byte[] rs = new byte[40];

        // offsetR - 原始signature中R值的起始位移
        // lengthR - 原始signature中R值的长度
        // startR - 目标signature中R值的起始位移
        int offsetR = 4;
        int lengthR = signature[offsetR - 1];
        int startR = 0;

        if (signature[offsetR - 2] != SEP_TAG) {
            throw new RuntimeException("Invalid signature format");
        }

        if (lengthR > 20) {
            offsetR += (lengthR - 20);
            lengthR = 20;
        } else if (lengthR < 20) {
            startR += (20 - lengthR);
        }

        // offsetS - 原始signature中S值的起始位移
        // lengthS - 原始signature中S值的长度
        // startS - 目标signature中S值的起始位移
        int offsetS = signature[3] + 6;
        int lengthS = signature[offsetS - 1];
        int startS = 20;

        if (signature[offsetS - 2] != SEP_TAG) {
            throw new RuntimeException("Invalid signature format");
        }

        if (lengthS > 20) {
            offsetS += (lengthS - 20);
            lengthS = 20;
        } else if (lengthS < 20) {
            startS += (20 - lengthS);
        }

        System.arraycopy(signature, offsetR, rs, startR, lengthR);
        System.arraycopy(signature, offsetS, rs, startS, lengthS);

        return rs;
    }

    /**
     * 将固定的40字节长的格式转换为Sun provider提供的signature的格式。
     */
    public static byte[] rsToSignature(byte[] rs) throws RuntimeException {
        if ((rs == null) || (rs.length != 40)) {
            throw new RuntimeException("Invalid signature format");
        }

        int length = 46;
        int offsetR = 4;
        int lengthR = 20;

        if ((rs[0] & 0x80) != 0) {
            length++;
            offsetR++;
            lengthR++;
        }

        int offsetS = offsetR + 22;
        int lengthS = 20;

        if ((rs[20] & 0x80) != 0) {
            length++;
            offsetS++;
            lengthS++;
        }

        byte[] signature = new byte[length];
        signature[0] = START_TAG;
        signature[1] = (byte) (length - 2);
        signature[2] = SEP_TAG;
        signature[3] = (byte) lengthR;
        System.arraycopy(rs, 0, signature, offsetR, 20);
        signature[offsetR + 20] = SEP_TAG;
        signature[offsetR + 21] = (byte) lengthS;
        System.arraycopy(rs, 20, signature, offsetS, 20);
        
        return signature;
    }

    private class KeyPairEntry {
        private PublicKey  publicKey;
        private PrivateKey privateKey;
    }
}
