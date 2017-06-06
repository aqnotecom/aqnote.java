/*
 * Copyright (C) 2013-2016 Peng Li<aqnote@qq.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.regex;

import com.aqnote.shared.lang.StringUtil;

import java.util.regex.Pattern;

public class RegexTest {

    public static void main(String[] args) {
        RegexTest test = new RegexTest();
        test.test01();
        test.test02();
        test.testPosix_lower();
    }

    public void test01() {
        String[] data = {
                        "https://login.m.taobao.com/login.htm?loginFrom=wap1688",
                        "https://login.m.taobao.com/login.htm?loginFrom=wap1688&b=b",
                        "https://login.m.taobao.com/login.htm?a=a&loginFrom=wap1688",
                        "https://login.m.taobao.com/login.htm?a=a&loginFrom=wap1688&b=b",
                        "https://login.m.taobao.com/login.htm&loginFrom=wap1688",
                        "https://login.m.taobao.com/login.htm?loginFrom=wap16889",
                        "https://login.m.taobao.com/login.htm?loginFrom=wap188",
                        "https://login.m.taobao.com/login.htm",
                        "https://login.m.taobao.com/login.htm?loginFrom=",
                        "https://login.m.taobao.com/jump",
                        "https://login.m.taobao.com/add"};
        // 1688 跳转过来的url
//        String pattern = "^(https|http)://login.m.taobao.com/login.htm(\\?)(loginFrom=wap1688$|.*&loginFrom=wap1688$|loginFrom=wap1688&.*$|.*&loginFrom=wap1688&.&$)";
        // 登陆url并且不是1688跳转过来
        //String pattern = "^((https|http)://)login.m.taobao.com/login.htm\\?(?!(loginFrom=wap1688$|.*&loginFrom=wap1688$|loginFrom=wap1688&.*$|.*&loginFrom=wap1688&.&$))";
        String pattern = "^(https|http)://login.m.taobao.com/login.htm(?!\\?loginFrom=wap1688$)";
        match(pattern, data);
    }

    public void test02() {
        String[] data = {
                "123566433",
                "1234566322.",
                ".134562111",
                "11191919a9919119"
        };
        String pattern = "^\\d+$";
        match(pattern, data);
    }

    public void testPosix_lower() {
        String[] data = {
                "a",
                "1",
                "A",
                "aa",
                "11"
        };
        String pattern = "\\p{Lower}";
        match(pattern, data);
    }

    public void match(String pattern, String[] data) {
        Pattern p = Pattern.compile(pattern, 0);
        for(int i=0; i < data.length; i++) {
            System.out.println(data[i] + " " + p.matcher(data[i]).matches());
        }
        System.out.println(StringUtil.repeat("=", 32));
    }

}
