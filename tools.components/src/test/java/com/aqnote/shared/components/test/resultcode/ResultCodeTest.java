/*
 * Copyright (C) 2013-2016 Peng Li<aqnote@qq.com>.
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
 * @author "Peng Li"<aqnote@qq.com>
 */
public class ResultCodeTest {

    @Test
    @Ignore
    public void test() {
        LabelResultCode code = LabelResultCode.AAA;
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
        System.out.println(LabelResultCode.AAA.getName());
        System.out.println(LabelResultCode.AAA.getMessage());
        // System.out.println(LabelResultCode.BBB.getMessage());
        // System.out.println(LabelResultCode.CCC.getMessage());
        // System.out.println(LabelResultCode.DDD.getMessage());
    }

    @Test
    @Ignore
    public void test3() {
        IResultCode[] elements = LabelResultCode.AAA.getClass().getEnumConstants();
        for (IResultCode element : elements) {
            System.out.println(element.getName());
        }
    }
}
