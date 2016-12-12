/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.domain;

import java.text.MessageFormat;

public class DomainAvaliableTest {

    public static void main(String[] args) throws InterruptedException {

        String url = "http://pandavip.www.net.cn/check/check_ac1.cgi?domain={0}.com&callback=jQuery17109914563717320561_1382233581324&_={1}";

        for (int i = 0; i < 26; i++) {
            Object[] params = new Object[2];

            String result = "";
            char tmp = (char) ('a' + i);
            result = result + tmp;

            long time = System.currentTimeMillis();

            params[0] = result;
            params[1] = time;
            MessageFormat.format(url, params);
            
            Thread.sleep(100);
        }
    }
}
