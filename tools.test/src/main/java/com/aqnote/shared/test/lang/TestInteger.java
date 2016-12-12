/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.lang;

import java.lang.reflect.Field;

/**
 * TestInteger.java desc：TODO
 * 
 * @author madding.lip Jun 3, 2014 9:43:01 AM
 */
public class TestInteger {

    public static void main(String[] args) throws Exception {
        Class<?> cache = Integer.class.getDeclaredClasses()[0];
        Field c = cache.getDeclaredField("cache");
        c.setAccessible(true);
        Integer[] array = (Integer[]) c.get(cache);
        array[132] = array[133];
        int i = 2 + 2;
        System.out.printf("%d", i);
    }
}

/**
 * output: 5
 * reason: 整型会把-128到127的整型包转类cache到Integer$IntegerCache，
 * 因此修改了array[132]=array[138+4]=4的值为array[133]=array[132+5]=5时，
 * IntegerCache值变来了，而2+2计算产生的支为4落在-128～127之间，结果即为array[128+4]=array[132l]=5;
 * 
 * 这里还有一个问题：为什么2+2会被转为包装类?
 * 因为jvm6会将2+2转成包装类机型计算，导致i从原子类型变成包装类型;
 */