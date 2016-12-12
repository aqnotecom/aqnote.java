/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.lang;

/**
 * TestChar.java desc：TODO 
 * @author madding.lip May 4, 2014 2:37:45 PM
 */
public class TestChar {

    public static void main(String[] args) {
        System.out.println('中');
        System.out.println("\uD83C\uDF4E");
        System.out.println("\uD83C\uDF4E".getBytes().length);
        System.out.println(Character.charCount(0x1f34e));
        System.out.println(Character.toChars(0x1f34e));
        System.out.println(Character.isValidCodePoint(0x1f34e));
    }
}
//1101100000111100 1101 1111 0100 1110
//               1 1111 0011 0100 1110