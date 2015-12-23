/*
 * Copyright madding.me.
 */
package com.madding.shared.encrypt.hash;

import com.madding.shared.encrypt.md.MD5;

import junit.framework.TestCase;

/**
 * 类MD5Test.java的实现描述：TODO 类实现描述 
 * @author madding.lip May 8, 2012 4:13:04 PM
 */
public class MD5Test extends TestCase {

    public void test() {
        // 18057129798
        System.out.println(MD5.md5("1367581598539102"));
        System.out.println(MD5.md5("113611"));
    }
}
