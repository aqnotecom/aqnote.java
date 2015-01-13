/*
 * Programmer-tools -- A develop code for dever to quickly analyse Copyright (C) 2013-2016 madding.lip
 * <madding.lip@gmail.com>. This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.madding.shared.misc;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

/**
 * UnsafeUtil.java desc：TODO
 * 
 * @author madding.lip Aug 4, 2014 11:43:27 AM
 */
public class UnsafeUtil {
    
    private static final String THE_UNSAFE = "theUnsafe";

    public static Unsafe getUnsafeInstance() throws Exception {
        Field theUnsafeInstance;
        try {
            theUnsafeInstance = Unsafe.class.getDeclaredField(THE_UNSAFE);
            theUnsafeInstance.setAccessible(true);
            return (Unsafe) theUnsafeInstance.get(Unsafe.class);
        } catch (SecurityException e) {
            throw e;
        } catch (NoSuchFieldException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalAccessException e) {
            throw e;
        }

    }
}
