/*
 * Copyright madding.me.
 */
package com.madding.shared.unicode;

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
