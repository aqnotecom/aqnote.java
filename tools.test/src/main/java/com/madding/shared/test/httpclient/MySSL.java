package com.madding.shared.test.httpclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


public class MySSL {
    public static void main(String[] args) throws Exception {
        String host = "login.alibaba-inc.com";
        String url = "/rpc/oauth/verify.json";
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
        SSLContext.setDefault(ctx);
        SSLSocketFactory factory = ctx.getSocketFactory();
        Socket socket = factory.createSocket(host, 443);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.write("GET " + url + " HTTP/1.0");
        out.flush();
        out.close();
        in.close();
    }
}
