/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.lang;

/**
 * 类ClassLoaderUtil.java的实现描述：classLoader加载类
 * 
 * @author madding.lip Sep 18, 2013 11:27:20 AM
 */
public class ClassLoaderUtil {

    public static ClassLoader getSystemClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        classLoader = (classLoader == null) ? getSystemClassLoader() : classLoader;
        return classLoader;
    }

    public static ClassLoader getClassLoader(ClassLoader classLoader) {
        return (classLoader == null) ? getClassLoader() : classLoader;
    }

}
