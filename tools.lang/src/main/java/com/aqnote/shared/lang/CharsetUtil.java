/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.lang;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

/**
 * CharsetUtil.java类描述：字符处理工具
 * 
 * @author madding.lip
 */
public class CharsetUtil {

    public static final String DEFAULT_CHARSET = "ISO-8859-1";

    /**
     * 取得正规的字符集名称, 如果指定字符集不存在, 则抛出<code>UnsupportedEncodingException</code>.
     * 
     * @param charset 字符集名称
     * @return 正规的字符集名称
     * @throws IllegalCharsetNameException 如果指定字符集名称非法
     * @throws UnsupportedCharsetException 如果指定字符集不存在
     */
    public static String getCanonicalCharset(String charset) {
        return Charset.forName(charset).name();
    }

    /**
     * 取得正规的字符集名称, 如果指定字符集不存在, 则返回<code>null</code>.
     * 
     * @param charset 字符集名称
     * @return 正规的字符集名称, 如果指定字符集不存在, 则返回<code>null</code>
     */
    public static String getCanonicalCharset(String charset, String defaultCharset) {
        String result = null;

        try {
            result = getCanonicalCharset(charset);
        } catch (IllegalArgumentException e) {
            if (defaultCharset != null) {
                try {
                    result = getCanonicalCharset(defaultCharset);
                } catch (IllegalArgumentException ee) {
                }
            }
        }

        return result;
    }
    
    /**
     * 获取系统编码
     */
    public static String getSystemCharset() {
        return getCanonicalCharset(new OutputStreamWriter(new ByteArrayOutputStream()).getEncoding(), DEFAULT_CHARSET);
    }
}
