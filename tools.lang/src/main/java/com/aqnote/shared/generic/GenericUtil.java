/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类GetGenericType.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Jul 16, 2013 11:07:00 AM
 */
public class GenericUtil {

    /**
     * 获取传入clazz的泛型类型
     * 
     * @param clazz 包含泛型的class
     * @param pos 泛型的位置
     * 
     * @return 获取到位置的泛型类型
     * 
     * @throws RuntimeException 参数错误会抛出运行时异常
     */
    public static Class<?> getType(final Class<?> clazz, final int pos) throws RuntimeException {
        
        final Type superclass = clazz.getGenericSuperclass();

        if (!(superclass instanceof ParameterizedType)) {
            throw new RuntimeException("This instance is not the an anonymous class");
        }

        final Type[] types = ((ParameterizedType) superclass).getActualTypeArguments();

        if (pos >= types.length) {
            throw new RuntimeException("pos out range of the Type array.");
        }

        if (!(types[pos] instanceof Class<?>)) {
            throw new RuntimeException("Generic type is not a class, you get is \"[T]\") " + types[pos]);
        }
        
        return (Class<?>) types[pos];
    }

}
