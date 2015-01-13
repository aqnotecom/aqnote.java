package com.madding.shared.misc;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeUtilTest {

    protected static int apple  = 10;
    protected int        orange = 10;

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = UnsafeUtil.getUnsafeInstance();

        Field appleField = UnsafeUtilTest.class.getDeclaredField("apple");
        System.out.println("Location of Apple: " + unsafe.staticFieldOffset(appleField));

        Field orangeField = UnsafeUtilTest.class.getDeclaredField("orange");
        System.out.println("Location of Orange: " + unsafe.objectFieldOffset(orangeField));
    }
}
