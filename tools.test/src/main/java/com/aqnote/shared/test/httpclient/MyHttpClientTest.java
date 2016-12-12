/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * Hello world!
 */
public class MyHttpClientTest {
    static PoolingClientConnectionManager connectionManager = null;
    static HttpClient                     httpclient        = null;
    
    static {
        connectionManager = new PoolingClientConnectionManager();
        connectionManager.setMaxTotal(1);
        httpclient = new DefaultHttpClient(connectionManager);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000);
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);
        httpclient.getParams().setParameter("http.connection-manager.timeout", 60*1000L);
//        httpclient.getParams().setParameter("http.protocol.head-body-timeout", 1000);
    }

    public static void main(String[] args) {

        System.setProperty("javax.net.debug", "ssl,handshake");
        System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol");

        for (int i = 0; true; i++) {
            new Thread(new MyTest(httpclient), "trhead-" + i).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}

class MyTest implements Runnable {
    static HttpClient                     httpclient        = null;

    public MyTest(HttpClient hc){
        httpclient = hc;
    }

    public void run() {
//        while (true) {
            HttpUriRequest httpget = new HttpGet("http://www.apache.org/");
            try {
                HttpResponse response = httpclient.execute(httpget);

                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    EntityUtils.toString(entity);
                    // System.out.println(EntityUtils.toString(entity));
                }
                System.out.println("----------------------------------------");

            } catch (ClientProtocolException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                if (httpget != null) {
                    httpget.abort();
                }
            }
//        }
    }

}

/*
 * Timer timer = new Timer(); timer.schedule(new TimerTask() { public void run() { if(httpget != null) {
 * httpget.abort(); } } }, 100);
 */
