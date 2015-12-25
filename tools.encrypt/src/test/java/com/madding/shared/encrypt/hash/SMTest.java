/*
 * Programmer-tools -- A develop code for dever to quickly analyse
 * Copyright (C) 2013-2016 madding.lip <madding.lip@gmail.com>.
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation;
 */
package com.madding.shared.encrypt.hash;

import java.io.UnsupportedEncodingException;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import junit.framework.TestCase;

/**
 * SMTest.java desc：TODO 
 * @author madding.lip Dec 24, 2015 6:19:59 PM
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SMTest extends TestCase {

    public void test01() throws UnsupportedEncodingException {
        System.out.println(SM._sm3("13675815985".getBytes("UTF-8")));   // 64bit
    }
}
