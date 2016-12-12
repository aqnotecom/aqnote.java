/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.test.resultcode;

import java.util.Locale;

import org.junit.Ignore;
import org.junit.Test;

import com.aqnote.shared.components.resultcode.IResultCode;
import com.aqnote.shared.lang.LocaleUtil;

/**
 * 提示和枚举关联测试类
 * 
 * @author madding.lip
 */
public class MyResultCodeTest {

    @Test
    @Ignore
    public void test() {
        MyResultCode code = MyResultCode.AAA;
        System.out.println(code.getClass().getName());
        System.out.println(code.getClass().isEnum());
        System.out.println(code.getName());
    }

    @Test
    @Ignore
    public void test1() {
        Locale locale = LocaleUtil.getContextLocale();
        System.out.println(locale.toString());
        System.out.println(locale.getLanguage());
        System.out.println(locale.getCountry());
    }

    @Test
    // @Ignore
    public void test2() {
        System.out.println(MyResultCode.AAA.getName());
        System.out.println(MyResultCode.AAA.getMessage());
        // System.out.println(MyResultCode.BBB.getMessage());
        // System.out.println(MyResultCode.CCC.getMessage());
        // System.out.println(MyResultCode.DDD.getMessage());
    }

    @Test
    @Ignore
    public void test3() {
        IResultCode[] elements = MyResultCode.AAA.getClass().getEnumConstants();
        for (IResultCode element : elements) {
            System.out.println(element.getName());
        }
    }
}
