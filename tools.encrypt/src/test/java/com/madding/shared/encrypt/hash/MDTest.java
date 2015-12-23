/*
 * Copyright madding.me.
 */
package com.madding.shared.encrypt.hash;

import com.madding.shared.encrypt.hash.MD;

import junit.framework.TestCase;

/**
 * 类MD5Test.java的实现描述：TODO 类实现描述 
 * @author madding.lip May 8, 2012 4:13:04 PM
 */
public class MDTest extends TestCase {

    public void test() {
        // 18057129798
        System.out.println(MD.md5("13675815985"));
        System.out.println(MD.md5("13675815986"));
    }
}
