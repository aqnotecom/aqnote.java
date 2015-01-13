/*
 * Programmer-tools -- A develop code for dever to quickly analyse Copyright (C) 2013-2016 madding.lip
 * <madding.lip@gmail.com>. This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.madding.shared.net.httpclient.trustmanager;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * CustomX509TrustManager.java desc：TODO
 * 
 * @author madding.lip May 12, 2014 1:45:24 PM
 */
public class CustomTrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//        throw new CertificateException("false");
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
