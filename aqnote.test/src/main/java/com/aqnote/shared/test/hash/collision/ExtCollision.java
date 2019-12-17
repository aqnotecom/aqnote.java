/*
 * Copyright (C) 2013-2016 Peng Li<aqnote@qq.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.hash.collision;

import com.aqnote.shared.test.hash.JavaDefaultHashAlgorithm;

/**
 * 罗列一批的hash的冲突
 *
 * @author "Peng Li"<aqnote@aqnote.com>
 */
public class ExtCollision {

    public static void main(String[] args) {
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "AaAaAaAa"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "BBBBBBBB"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "AaBBBBBB"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "BBAaBBBB"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "BBBBAaBB"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "BBBBBBAa"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "AaAaBBBB"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "AaBBAaBB"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "AaBBBBAa"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "BBAaAaBB"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "BBAaBBAa"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "BBBBAaAa"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "AaAaAaBB"));
        System.out.println(JavaDefaultHashAlgorithm.sameHashcode("AaAaAaAa", "BBAaAaAa"));

    }

}
