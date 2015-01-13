package com.madding.shared.net.httpclient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.madding.shared.net.httpclient.trustmanager.CustomTrustManager;
import com.madding.shared.net.httpclient.trustmanager.ServerTrustManager;

public class SSLContexCreator {

    public static SSLContext createSSLContext() {
        try {
            SSLContext tmpContext = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = new TrustManager[] { new CustomTrustManager() };

            tmpContext.init(null, trustManagers, null);

            return tmpContext;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static SSLContext createMySSLContext() {
        try {
            SSLContext tmpContext = SSLContext.getInstance("TLS");
            // TrustManager[] trustManagers = new TrustManager[] { new ClientTrustManager(null), new
            // ServerTrustManager() };
            TrustManager[] trustManagers = new TrustManager[] { new ServerTrustManager() };

            tmpContext.init(null, trustManagers, null);

            return tmpContext;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

}
