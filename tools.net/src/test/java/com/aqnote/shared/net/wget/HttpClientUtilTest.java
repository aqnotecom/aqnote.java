/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.wget;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import junit.framework.TestCase;

import com.aqnote.shared.charset.Charset;
import com.aqnote.shared.net.httpclient.HttpClientUtil;

/**
 * 类WgetTest.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip May 8, 2012 2:53:15 PM
 */
public class HttpClientUtilTest extends TestCase {

    String domain;
    String uid;
    String pwd;
    String receiver;
    String encode;
    String content;

    public void setUp() throws Exception {
        // "http://http.c123.com"; // 5a5ed7f37f07d4c679e86d71e0bf29d2
        domain = "http://http.smstong.com"; // 562493c9f17f153f9467b663ed793fda
        uid = "143388";
        pwd = "562493c9f17f153f9467b663ed793fda";
        receiver = "13675815985";
        encode = "GBK";
        content = URLEncoder.encode("你的验证码是:485949", encode);
    }

    public void testSendMessage() throws UnsupportedEncodingException {

        String url = domain + "/tx/?uid=" + uid + "&pwd=" + pwd + "&mobile=" + receiver + "&content=" + content;
        String result = HttpClientUtil.get(url, Charset.UTF_8);

        System.out.println("======================SendMessage======================");
        System.out.println("url: " + url);
        System.out.println("result: " + result);
    }

    public void testRecevierMessage() {
        String url = domain + "/rx/?uid=" + uid + "&pwd=" + pwd;
        String result = HttpClientUtil.get(url, Charset.UTF_8);

        System.out.println("======================RecevierMessage======================");
        System.out.println("url: " + url);
        System.out.println("result: " + result);
    }

    public void testRemainCount() {
        String url = domain + "/mm/?uid=" + uid + "&pwd=" + pwd;
        String result = HttpClientUtil.get(url, Charset.UTF_8);

        System.out.println("======================RemainCount======================");
        System.out.println("url: " + url);
        System.out.println("result: " + result);
    }
}

// 100 发送成功
// 101 验证失败
// 102 短信不足
// 103 操作失败
// 104 非法字符
// 105 内容过多
// 106 号码过多
// 107 频率过快
// 108 号码内容空
// 109 账号冻结
// 110 禁止频繁单条发送
// 111 系统暂定发送
// 120 系统升级
