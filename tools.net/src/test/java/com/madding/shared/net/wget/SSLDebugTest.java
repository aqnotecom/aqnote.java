package com.madding.shared.net.wget;

/*
 * Copyright madding.me.
 */

import com.madding.shared.charset.Charset;
import com.madding.shared.net.httpclient.HttpClientUtil;

/**
 * 类WgetTest.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip May 8, 2012 2:53:15 PM
 */
public class SSLDebugTest {

    public static void main(String[] args) throws Exception {

        scene_truststore();
    }

    public static void scene_trustmanager() {
        System.getProperties().setProperty("javax.net.debug", "ssl,handshake,data,trustmanager");
        HttpClientUtil.initHttpClient(true);
        // String response = HttpClientUtil.get("https://login.alibaba-inc.com", Charset.UTF_8);
        String response = HttpClientUtil.get("https://alilang.alibaba-inc.com", Charset.UTF_8);
        System.out.println(response);
    }

    public static void scene_truststore() throws Exception {
        System.setProperty("javax.net.ssl.trustStore", "/home/madding/certs/test.keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        // System.setProperty("javax.net.ssl.keyStore", "/home/madding/certs/test.keystore");
        // System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        // char[] passphrase = "123456".toCharArray();
        // KeyStore ks = KeyStore.getInstance("JKS");
        // ks.load(new FileInputStream("/home/madding/certs/test.keystore"), passphrase);

//        System.getProperties().setProperty("javax.net.debug", "ssl");

        HttpClientUtil.initHttpClient(false);
        String response = HttpClientUtil.get("https://login.alibaba-inc.com", Charset.UTF_8);
        // String response = HttpClientUtil.get("https://alilang.alibaba-inc.com", Charset.UTF_8);
        System.out.println(response);
    }
}
