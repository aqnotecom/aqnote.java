/*
 * Programmer-tools -- A develop code for dever to quickly analyse Copyright (C) 2013-2016 madding.lip
 * <madding.lip@gmail.com>. This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.madding.shared.encrypt.util;

/**
 * Hex.java desc：TODO
 * 
 * @author madding.lip Dec 23, 2015 4:31:14 PM
 */
public class Hex {

    // 用来将字节转换成 16 进制表示的字符
    private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
                                        'f' };

    public final static char[] encodeHex(byte[] data) {
        if (data == null) return null;
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = hexDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = hexDigits[0x0F & data[i]];
        }
        return out;
    }
}