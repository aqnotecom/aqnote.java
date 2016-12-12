/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.httpclient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.aqnote.shared.net.httpclient.trustmanager.ClientTrustManager;
import com.aqnote.shared.net.httpclient.trustmanager.ServerTrustManager;
import com.aqnote.shared.net.httpclient.trustmanager.TrustAllManager;
import com.aqnote.shared.net.httpclient.trustmanager.UnTrustAllManager;

public class SSLContexCreator {

    public static SSLContext createTrustAllSSLContext() {
        try {
            SSLContext tmpContext = SSLContext.getInstance("TLS1.2");
            TrustManager[] trustManagers = new TrustManager[] { new TrustAllManager() };

            tmpContext.init(null, trustManagers, null);

            return tmpContext;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static SSLContext createTrustServerSSLContext() {
        try {
            SSLContext tmpContext = SSLContext.getInstance("TLS1.2");
            TrustManager[] trustManagers = new TrustManager[] { new ServerTrustManager() };

            tmpContext.init(null, trustManagers, null);

            return tmpContext;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
    
    public static SSLContext createTrustSystemSSLContext() {
        try {
            SSLContext tmpContext = SSLContext.getInstance("TLS1.2");
             TrustManager[] trustManagers = new TrustManager[] { new ClientTrustManager(null), new
             ServerTrustManager() };
            tmpContext.init(null, trustManagers, null);

            return tmpContext;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static SSLContext createUnTrustAllSSLContext() {
        try {
            SSLContext tmpContext = SSLContext.getInstance("TLS1.2"); // TLS/TLS1/TLS1.1/TLS1.2
            TrustManager[] trustManagers = new TrustManager[] { new UnTrustAllManager() };

            tmpContext.init(null, trustManagers, null);

            return tmpContext;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
