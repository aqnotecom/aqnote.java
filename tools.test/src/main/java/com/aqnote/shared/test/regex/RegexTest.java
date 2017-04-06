/*
 * Copyright (C) 2013-2016 aqnote.com<aqnote.com@gmail.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.regex;

import java.util.regex.Pattern;

public class RegexTest {

    public static void main(String[] args) {
        RegexTest test = new RegexTest();
//        test.test01();
        test.test02();
    }

    public void test01() {
        String[] data = {
                         "https://login.m.taobao.com/login.htm?loginFrom=wap1688&abc=c",
                          "https://login.m.taobao.com/login.htm?abc=&loginFrom=wap1688",
                          "https://login.m.taobao.com/login.htm&loginFrom=wap1688",
                          "https://login.m.taobao.com/login.htm?loginFrom=wap16889",
                          "https://login.m.taobao.com/login.htm?loginFrom=wap188",
                          "https://login.m.taobao.com/login.htm",
                          "https://login.m.taobao.com/login.htm?loginFrom="
                          };
        String pattern = "((https|http)://)login.m.taobao.com/login.htm(\\?)?.*(!loginFrom=wap1688$)(!loginFrom=wap1688&).*";
        match(pattern, data);
    }

    public void test02() {
        String[] data = {
                "123566433",
                "1234566322.",
                ".134562111",
                "11191919a9919119"
        };
        String pattern = "^\\d{n}$";
        match(pattern, data);
    }

    public void match(String pattern, String[] data) {
        Pattern p = Pattern.compile(pattern);
        for(int i=0; i < data.length; i++) {
            System.out.println(data[i] + " " + p.matcher(data[i]).matches());
        }
    }
}
