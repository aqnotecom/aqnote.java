/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.code;
/*
 * Copyright madding.me.
 */


import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Hex;

/**
 * 类Code.java的实现描述：TODO 类实现描述 
 * @author madding.lip Jul 12, 2012 10:24:58 AM
 */
public class Code extends TestCase {
    public void test() throws UnsupportedEncodingException {
        String word = "中文";
        System.out.println(Hex.encodeHex(word.getBytes("unicode")));
        
        System.out.println(Hex.encodeHex(word.getBytes("utf-8")));
        
        System.out.println(Hex.encodeHex(word.getBytes("gbk")));
        System.out.println(Hex.encodeHex(word.getBytes("gb2312")));
        
        System.out.println(Hex.encodeHex(word.getBytes("iso8859_1")));
        System.out.println(Hex.encodeHex(word.getBytes("ascii")));
    }
}

/*
feff4e2d6587
e4b8ade69687
d6d0cec4
d6d0cec4
3f3f
3f3f
 */
