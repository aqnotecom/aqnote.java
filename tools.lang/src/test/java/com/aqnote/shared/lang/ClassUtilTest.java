/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.lang;

import java.io.Serializable;

import org.junit.Test;

import com.aqnote.shared.lang.ClassUtil;

/**
 * 根据传入的类型作类型转换
 * 
 * @author madding.lip
 */
public class ClassUtilTest {
    
    @Test
    public void testCase() {
        System.out.println(ClassUtil.converObj(getObj(12345), int.class));
        System.out.println(ClassUtil.converObj(getObj(true), boolean.class));
        System.out.println(ClassUtil.converObj(getObj(12345), Integer.class));
    }

    private Object getObj(Serializable serial) {
        return serial;
    }
}
