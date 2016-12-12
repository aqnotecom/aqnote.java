/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.unicode;

/**
 * 类Unicode.java的实现描述：TODO 类实现描述 
 * @author madding.lip Jul 5, 2012 9:19:47 AM
 */
public class Unicode {

    public static void main(String[] args) {
        String unicode = "\u8f6c\u5316";
        System.out.println(unicode);
        
        for(int i=0; i<10; i++) {
            unicode = "\u0000" + i;
            System.out.println(unicode);
        }
        
    }
}
