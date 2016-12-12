/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.httpclient.trustmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author madding.lip
 */
public class ServerTrustManager implements X509TrustManager {
	Certificate cert = null;

	public ServerTrustManager() throws Exception {

		InputStream ins = null;
		CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
		try {
		    File crt = new File("/home/madding/certs/1.crt");
			ins = new FileInputStream(crt);
			// 读取证书
			this.cert = cerFactory.generateCertificate(ins);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != ins) {
				ins.close();
				ins = null;
			}
		}

	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		System.out.println("checkClientTrusted");
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String paramString)
			throws CertificateException {
		for (int i = 0; i < chain.length; i++) {
			X509Certificate cert = chain[i];
			if (cert == null || cert.getSubjectDN() == null
					|| cert.getSubjectDN().getName() == null) {
				continue;
			}
			if (cert.getSubjectDN().getName().contains("mad")) {
				// 验证时间有效性
				cert.checkValidity();
				// 验证签名

				// try {
				PublicKey pkey = this.cert.getPublicKey();
				PublicKey pkey2 = cert.getPublicKey();
				if (pkey2.toString().equalsIgnoreCase(pkey.toString())) {
					// return when verify success
					return;
				} else {
					break;
				}
			}
		}
		throw new CertificateException("publicKey verify failed!");
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
