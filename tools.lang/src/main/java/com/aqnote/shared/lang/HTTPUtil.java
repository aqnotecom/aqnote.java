/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HTTPUtil {

    public static final String QUESTION      = "?";
    public static final String EQUALS        = "=";
    public static final String SLASH         = "/";
    public static final String AMPERSAND     = "&";
    public static final String ENCODING_UTF8 = "UTF-8";

    public static String retrieve(String url) throws IOException, KeyManagementException, NoSuchAlgorithmException,
                                             NoSuchProviderException {
        return retrieve(url, null);
    }

    public static String retrieve(String url, Map<String, String> postdatas) throws IOException,
                                                                            KeyManagementException,
                                                                            NoSuchAlgorithmException,
                                                                            NoSuchProviderException {
        BufferedReader r = null;
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
//            if ((connection instanceof sun.net.www.protocol.https.HttpsURLConnectionImpl)) {
//                ((sun.net.www.protocol.https.HttpsURLConnectionImpl) connection).setSSLSocketFactory(createSSLSocketFactory());
//                ((sun.net.www.protocol.https.HttpsURLConnectionImpl) connection).setHostnameVerifier(new TrustAnyHostnameVerifier());
//            }
            connection.setRequestProperty("Connection", "close");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(0x0);
            connection.setReadTimeout(0x0);
            if (postdatas != null) {
                connection.setRequestMethod("POST");// POST提交模式
                StringBuffer param = new StringBuffer();
                for (String key : postdatas.keySet()) {
                    param.append(AMPERSAND);
                    param.append(key).append(EQUALS).append(postdatas.get(key));
                }
                connection.getOutputStream().write(param.toString().getBytes());
                connection.getOutputStream().flush();
                connection.getOutputStream().close();
            }
            r = new BufferedReader(new InputStreamReader(connection.getInputStream(), ENCODING_UTF8));
            return read(r);
        } finally {
            try {
                if (r != null) r.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String retrieveWithErrorHandler(String url, Map<String, String> postdatas) throws IOException,
                                                                                            KeyManagementException,
                                                                                            NoSuchAlgorithmException,
                                                                                            NoSuchProviderException {
        BufferedReader r = null;
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestProperty("Connection", "close");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            if (postdatas != null) {
                connection.setRequestMethod("POST");// POST提交模式
                StringBuffer param = new StringBuffer();
                for (String key : postdatas.keySet()) {
                    param.append(AMPERSAND);
                    param.append(key).append(EQUALS).append(postdatas.get(key));
                }
                connection.getOutputStream().write(param.toString().getBytes());
                connection.getOutputStream().flush();
                connection.getOutputStream().close();
            }
//            if ((connection instanceof sun.net.www.protocol.https.HttpsURLConnectionImpl)) {
//                ((sun.net.www.protocol.https.HttpsURLConnectionImpl) connection).setSSLSocketFactory(createSSLSocketFactory());
//                ((sun.net.www.protocol.https.HttpsURLConnectionImpl) connection).setHostnameVerifier(new TrustAnyHostnameVerifier());
//            }
            int retCode = connection.getResponseCode();
            // receive ,用recIn判断是否需要返回流形式
            InputStream inputStream = null;
            if (retCode >= 400) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }
            String result = "";
            if (inputStream != null) {
                r = new BufferedReader(new InputStreamReader(inputStream, ENCODING_UTF8));
                result = read(r);
            }
            return result;
        } finally {
            try {
                if (r != null) r.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() throws NoSuchAlgorithmException, NoSuchProviderException,
                                                            KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new SecureRandom());
        return sslContext.getSocketFactory();
    }

    public static String read(Reader reader) {
        try {
            final int DEFAULT_BUFFER_SIZE = 1024 * 4;

            StringWriter writer = new StringWriter();

            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }

            return writer.toString();
        } catch (IOException ex) {
            throw new IllegalStateException("read error", ex);
        }
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static String createUrlWithoutParam(String fullUrl, String paramName, String paramValue) {
        String keyValue = paramName + EQUALS + paramValue;
        if (fullUrl.contains(keyValue + AMPERSAND)) {
            fullUrl = fullUrl.replace(keyValue + AMPERSAND, "");
        } else fullUrl = fullUrl.replace(keyValue, "");
        if (fullUrl.endsWith(AMPERSAND) || fullUrl.endsWith(QUESTION)) {
            fullUrl = fullUrl.substring(0, fullUrl.length() - 1);
        }
        return fullUrl;
    }

    public static Map<String, String> splitQueryString(String queryString) {
        Map<String, String> paramMap = new HashMap<String, String>();
        if (queryString == null) return paramMap;
        String[] params = queryString.split(AMPERSAND);
        if (params != null) {
            for (String param : params) {
                if (param != null) {
                    String[] kv = param.split(EQUALS);
                    if (kv != null && kv.length > 0) {
                        if (kv.length >= 2) paramMap.put(kv[0], kv[1]);
                        else paramMap.put(kv[0], "");
                    }
                }
            }
        }
        return paramMap;
    }

    /**
     * 对url中的
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public static String encodeUrl(String urlStr) throws Exception {
        if (StringUtil.isBlank(urlStr)) return urlStr;
        URL url = new URL(urlStr);
        String queryString = url.getQuery();
        String path = url.getPath();
        String encodePath = URLEncoder.encode(path, "UTF-8").replace("%2F", "/");
        if (StringUtil.isNotBlank(queryString)) {
            Map<String, String> queries = splitQueryString(queryString);
            String newQueryString = "";
            for (String key : queries.keySet()) {
                String value = queries.get(key);
                // 防止原来已是encode状态的值重复encode，如果decode后结果不一样，说明已经是encode的
                if (!value.equals(URLDecoder.decode(queries.get(key), "UTF-8"))) {
                    newQueryString += "&" + key + "=" + value;
                } else newQueryString += "&" + key + "=" + URLEncoder.encode(value, "UTF-8");
            }
            // 移除第一个多余&
            newQueryString = newQueryString.substring(1);
            urlStr = urlStr.replace(queryString, newQueryString);
        }
        if (StringUtil.isNotBlank(path) && StringUtil.isNotBlank(encodePath)) {
            urlStr = urlStr.replace(path, encodePath);
        }
        return urlStr;
    }

    public static String getHost(String backUrl) {
        String host = "";
        try {
            int index = backUrl.indexOf('?');
            if (index > 0) {
                backUrl = backUrl.substring(0, index);
            }
            backUrl = URLDecoder.decode(backUrl, "UTF-8");
            URL url = new URL(backUrl);
            host = url.getHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return host;
    }

    public static String replaceSpecialCharacters(String url) {
        if (StringUtil.isBlank(url)) return url;
        else return url.replace(" ", "%20").replace("|", "%7C").replace("{", "%7B").replace("}", "%7D");
    }

}
