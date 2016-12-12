/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.generic;

import java.util.HashMap;
import java.util.Vector;

import com.aqnote.shared.generic.GenericUtil;

import junit.framework.TestCase;

/**
 * 泛型解析测试类
 * 
 * @author madding.lip 
 */
public class GenericUtilTest extends TestCase {

    @SuppressWarnings("serial")
    public void testAnonation() {
        System.out.println("1:" + Vector.class);
        System.out.println("2:" + new Vector<String>().getClass());
        System.out.println("3:" + new Vector<String>() {
        }.getClass());
        System.out.println("4:" + new Vector<String>() {
        }.getClass());
        System.out.println("5:" + new Vector<String>() {
        }.getClass());
        System.out.println("6:" + new Vector<String>() {
        }.getClass());
        System.out.println("7:" + new Vector<String>() {
        }.getClass());
        System.out.println("8:" + new Vector<String>() {
        }.getClass());
    }

    public void testMain() {
        
        try {
            System.out.println(GenericUtil.getType(new String().getClass(), 0));
        } catch (RuntimeException re) {
            System.out.println("a1:" + re.getMessage());
        }

        try {
            System.out.println(GenericUtil.getType(new Vector<String>().getClass(), 0));

        } catch (RuntimeException re) {
            System.out.println("a2:" + re.getMessage());
        }

        try {
            System.out.println(GenericUtil.getType(new Vector<String>() {
                private static final long serialVersionUID = 6357643270642442374L;
            }.getClass(), 0));

        } catch (RuntimeException re) {
            System.out.println("a3:" + re.getMessage());
        }

        try {
            System.out.println(GenericUtil.getType(new HashMap<Long, Integer>() {
                private static final long serialVersionUID = 2508336389925392716L;
            }.getClass(), 0));

            System.out.println(GenericUtil.getType(new HashMap<Long, Integer>() {
                private static final long serialVersionUID = -9220380945561813218L;
            }.getClass(), 1));

        } catch (RuntimeException re) {
            System.out.println("a4:" + re.getMessage());
        }
    }

}
