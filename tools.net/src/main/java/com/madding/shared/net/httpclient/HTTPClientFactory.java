/*
 * Copyright 2013 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.madding.shared.net.httpclient;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;

/**
 * 类HTTPClientFactory.java的实现描述：httpclient工厂类
 * 
 * @author madding.lip Apr 8, 2013 1:54:37 PM
 */
public class HTTPClientFactory {

    public static final String SCHEME_NAME_HTTPS     = "https";
    public static final String SCHEME_NAME_HTTP      = "http";
    public static final String TLS_NAME              = "TLS";
    public static final int    SCHEME_PORT_HTTPS     = 443;
    public static final int    SCHEME_PORT_HTTP      = 80;

    public static final int    DEFAULT_SO_TIMEOUT    = 2000;
    public static final int    DEFAULT_CON_TIMEOUT   = 2000;
    public static final int    DEFAULT_MAX_TOTAL     = 32;
    public static final int    DEFAULT_MAX_PER_ROUTE = 4;

    private static HttpClient  httpClient;

    public static HttpClient getInstance(int soTimeout, int conTimout) {
        if (httpClient == null) {
            HttpParams httpParams = new SyncBasicHttpParams();
            int mySoTimeout = soTimeout >= 0 ? soTimeout : DEFAULT_SO_TIMEOUT;
            int myConTimeout = conTimout >= 0 ? conTimout : DEFAULT_CON_TIMEOUT;
            httpParams.setParameter(HttpConnectionParams.SO_TIMEOUT, mySoTimeout);
            httpParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, myConTimeout);

            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme(SCHEME_NAME_HTTP, SCHEME_PORT_HTTP,
                                               PlainSocketFactory.getSocketFactory()));

            SSLSocketFactory ssf = new SSLSocketFactory(SSLContexCreator.createSSLContext(),
                                                        SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            schemeRegistry.register(new Scheme(SCHEME_NAME_HTTPS, SCHEME_PORT_HTTPS, ssf));

            PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager(schemeRegistry);
            connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL);
            connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
            httpClient = new DefaultHttpClient(connectionManager, httpParams);
        }

        return httpClient;
    }

    public static HttpClient getMyInstance(int soTimeout, int conTimout) {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(SCHEME_NAME_HTTP, SCHEME_PORT_HTTP, PlainSocketFactory.getSocketFactory()));

        SSLSocketFactory ssf = new SSLSocketFactory(SSLContexCreator.createMySSLContext(),
                                                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        schemeRegistry.register(new Scheme(SCHEME_NAME_HTTPS, SCHEME_PORT_HTTPS, ssf));

        HttpParams httpParams = new SyncBasicHttpParams();
        httpParams.setParameter(HttpConnectionParams.SO_TIMEOUT, 10 * 1000);
        httpParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10 * 1000);
        httpParams.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);

        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager(schemeRegistry);
        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);

        return new DefaultHttpClient(connectionManager, httpParams);
    }
}
