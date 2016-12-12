/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.misc;

/**
 * UnsafeUtil.java desc：TODO
 * 
 * @author madding.lip Aug 4, 2014 11:43:27 AM
 */
public class UnsafeUtil {

    private static final String THE_UNSAFE = "theUnsafe";

//    public static Unsafe getUnsafeInstance() {
//        try {
//            Field UnsafeFiled = Unsafe.class.getDeclaredField(THE_UNSAFE);
//            UnsafeFiled.setAccessible(true);
//            return (Unsafe) UnsafeFiled.get(Unsafe.class);
//        } catch (SecurityException | NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
