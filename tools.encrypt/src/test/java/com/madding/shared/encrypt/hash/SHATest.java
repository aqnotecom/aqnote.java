/*
 * Programmer-tools -- A develop code for dever to quickly analyse
 * Copyright (C) 2013-2016 madding.lip <madding.lip@gmail.com>.
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation;
 */
package com.madding.shared.encrypt.hash;

import junit.framework.TestCase;

/**
 * SHATest.java desc：TODO 
 * @author madding.lip Dec 23, 2015 4:38:43 PM
 */
public class SHATest extends TestCase {
    public void test() {
        System.out.println(SHA.sha("13675815985"));         // 40bit
        System.out.println(SHA.sha1("13675815985"));        // 40bit
        System.out.println(SHA.sha3_224("13675815985"));    // 56bit BC
        System.out.println(SHA.sha224("13675815985"));      // 56bit BC
        System.out.println(SHA.sha3_256("13675815985"));    // 64bit BC
        System.out.println(SHA.sha256("13675815985"));      // 64bit BC
        System.out.println(SHA.sha384("13675815985"));      // 96bit
        System.out.println(SHA.sha512("13675815985"));      // 128bit
        
        
    }
}
