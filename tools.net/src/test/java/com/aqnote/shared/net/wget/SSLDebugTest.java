/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.wget;

import com.aqnote.shared.charset.Charset;
import com.aqnote.shared.net.httpclient.HttpClientUtil;

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
        String response = HttpClientUtil.get("https://www.alipay.com", Charset.UTF_8);
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

        // System.getProperties().setProperty("javax.net.debug", "ssl");

        HttpClientUtil.initHttpClient(false);
        String response = HttpClientUtil.get("https://www.alipay.com", Charset.UTF_8);
        System.out.println(response);
    }
}
