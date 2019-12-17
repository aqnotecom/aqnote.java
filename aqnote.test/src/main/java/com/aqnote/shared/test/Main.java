/*
 * Copyright (C) 2013-2016 Peng Li<aqnote@qq.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

public class Main {

    public static void main(String[] args)
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Main main = new Main();
        main.setUp();
    }

    public static Method[] getTestMethods(Class<?> clazz) {
        List<Method> result = new ArrayList<Method>();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                int modifiers = method.getModifiers();
                if (!StringUtils.startsWithIgnoreCase(method.getName(), "test")) {
                    continue;
                }
                ;
                if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                    result.add(method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return result.toArray(new Method[result.size()]);
    }

    private Class<?> clazz;
    private Object object;

    public void setUp() {
        clazz = getClass();
        try {
            object = clazz.getDeclaredConstructors().getClass(); // clazz.newInstance();
            Method[] accessibleMethodArray = getTestMethods(clazz);

            System.out.printf("[%s] start\n", clazz.getSimpleName());

            for (Method method : accessibleMethodArray) {
                String methodName = method.getName();
                Type[] pType = method.getGenericParameterTypes();
                if ((pType.length != 1) || Locale.class.isAssignableFrom(pType[0].getClass())) {
                    continue;
                }

                System.out.format("invoking %s()\n", methodName);
                method.setAccessible(true);
                Object o = method.invoke(object);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            System.out.format("[%s] end\n", clazz.getSimpleName());
        }

    }
}
