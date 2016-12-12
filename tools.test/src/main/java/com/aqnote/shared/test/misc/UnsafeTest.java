/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.misc;

public class UnsafeTest {

    protected static int apple  = 10;
    protected int        orange = 10;

    public static void main(String[] args) throws Exception {
//         Unsafe unsafe = UnsafeUtil.getUnsafeInstance();

        // Field appleField = UnsafeUtilTest.class.getDeclaredField("apple");
        // System.out.println("Location of Apple: " + unsafe.staticFieldOffset(appleField));

        // Field orangeField = UnsafeUtilTest.class.getDeclaredField("orange");
        // System.out.println("Location of Orange: " + unsafe.objectFieldOffset(orangeField));
    }
}

/**
 * Unsafe:
 *  - Info: Just returns some low-level memory information.
 *      - addressSize
 *      - pageSize
 *  - Objects: Provides methods for object and its fields manipulation.
 *      - allocateInstance
 *      - objectFieldOffset
 *  - Classes: Provides methods for classes and static fields manipulation.
 *      - staticFieldOffset
 *      - defineClass
 *      - defineAnonymousClass
 *      - ensureClassInitialized
 *  - Arrays: Arrays manipulation.
 *      - arrayBaseOffset
 *      - arrayIndexScale
 *  - Synchronization: Low level primitives for synchronization.
 *      - monitorEnter
 *      - tryMonitorEnter
 *      - monitorExit
 *      - compareAndSwapInt
 *      - putOrderedInt
 *  - Memory: Direct memory access methods.
 *      - allocateMemory
 *      - copyMemory
 *      - freeMemory
 *      - getAddress
 *      - getInt
 *      - putInt
 *  - 
 */
