/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.wget;

import com.aqnote.shared.charset.Charset;
import com.aqnote.shared.net.httpclient.HttpClientUtil;

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
