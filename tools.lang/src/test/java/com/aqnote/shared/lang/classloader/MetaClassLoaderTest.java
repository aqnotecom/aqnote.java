/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.lang.classloader;

import java.lang.reflect.Method;

import com.aqnote.shared.lang.classloader.MetaClassLoader;

public class MetaClassLoaderTest {

    static public void main(String args[]) throws Exception {
        MetaClassLoader classloader = new MetaClassLoader();
        Class<?> c = classloader.loadClass(args[0]);
        Class<?> argsClass[] = { "1".getClass() };
        Method m = c.getMethod("main", argsClass);
        String[] my1 = { "arg1 passed", "arg2 passed" };
        Object myarg1[] = { my1 };
        m.invoke(null, myarg1);

    }
}
