package com.madding.shared.net.wget;

/*
 * Copyright madding.me.
 */

import com.madding.shared.charset.Charset;
import com.madding.shared.net.httpclient.HttpClientUtil;

/**
 * 类WgetTest.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip May 8, 2012 2:53:15 PM
 */
public class HttpClientUtilTest2 {

    public static void main(String[] args) {
        HttpClientUtil.initHttpClient(true);
        String response = HttpClientUtil.get("https://www.alipay.com", Charset.UTF_8);
        System.out.println(response);
    }
}
