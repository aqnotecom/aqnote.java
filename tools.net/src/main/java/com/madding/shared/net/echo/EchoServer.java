package com.madding.shared.net.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class EchoServer {

    public static void main(String[] arstring) {
        
        System.setProperty("javax.net.ssl.keyStore", "echo.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        
        try {
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(9999);
            SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

            InputStream inputstream = sslsocket.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            
            OutputStream outputStream = sslsocket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            String string = null;
            while ((string = bufferedreader.readLine()) != null) {
                System.out.println(string);
                System.out.flush();
                bufferedWriter.write(string);
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

/**
 * 
 * keytool -genkey -keystore echo.keystore -keyalg RSA
 * 
 * javac EchoServer.java
 * java com.madding.shared.net.echo.EchoServer
 * 
 * javac EchoClient.java
 * java com.madding.shared.net.echo.EchoClient
 * 
 * debug
 *  -Djava.protocol.handler.pkgs=com.sun.net.ssl.internal.www.protocol -Djavax.net.debug=ssl
 * 
 */
