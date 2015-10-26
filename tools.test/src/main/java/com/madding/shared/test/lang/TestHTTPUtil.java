package com.madding.shared.test.lang;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import com.madding.shared.lang.HTTPUtil;

public class TestHTTPUtil {

	public static void main(String[] args) throws KeyManagementException,
			NoSuchAlgorithmException, NoSuchProviderException, IOException,
			InterruptedException, CertificateException, KeyStoreException {

//		System.setProperty("javax.net.ssl.keyStore", "/home/madding/certs/test.keystore");
//		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		
		System.setProperty("javax.net.ssl.trustStore", "/home/madding/certs/test.keystore");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		
//		System.setProperty( "javax.net.debug", "ssl");

		Map<String, String> postdatas = new HashMap<String, String>();
		postdatas.put("1", "2");
		
		String response = HTTPUtil.retrieve("https://www.alipay.com/", postdatas);
		
		System.out.println(response);
		
//		System.out.println(System.getProperty("javax.net.ssl.trustStore"));
//		System.out.println(System.getProperty("javax.net.ssl.keyStore"));
	}

}
