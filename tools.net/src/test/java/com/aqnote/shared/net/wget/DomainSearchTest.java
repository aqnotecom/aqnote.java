/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.wget;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import org.junit.Test;

import com.aqnote.shared.charset.Charset;
import com.aqnote.shared.net.httpclient.HttpClientUtil;

public class DomainSearchTest {

    @Test
    public void testSendMessage() throws UnsupportedEncodingException, InterruptedException {

        String _url = "http://pandavip.www.net.cn/check/check_ac1.cgi?domain={0}.com&callback=jQuery17109914563717320561_1382233581324&_={1}";

        for (int i = 0; i < 26; i++) {
            char atmp = (char) ('a' + 25 - i);

            Object[] params = new Object[2];
            String doamin = "";

            for (int j = 0; j < 26; j++) {
                char btmp = (char) ('a' + 25 - j);

                for (int k = 0; k < 26; k++) {
                    char ctmp = (char) ('a' + 25 - k);

                    for (int l = 0; l < 26; l++) {
                        char dtmp = (char) ('a' + 25 - l);
                        //
                        // for (int m = 0; m < 26; m++) {
                        // char etmp = (char) ('a' + m);

                        doamin = "" + atmp + btmp + ctmp + dtmp;

                        long time = System.currentTimeMillis();

                        params[0] = doamin;
                        params[1] = time;
                        String url = MessageFormat.format(_url, params);

                        String result = HttpClientUtil.get(url, Charset.UTF_8);

                        if (!result.contains("Domain name is not available")) {
                            System.out.println(result);

                        }

                        Thread.sleep(1000);
                        // }
                    }
                }
            }
        }

    }
}
