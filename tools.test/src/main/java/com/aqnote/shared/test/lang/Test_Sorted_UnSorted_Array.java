/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.lang;

import java.util.Arrays;
import java.util.Random;

/**
 * Test_Sorted_UnSorted_Array.java desc：Why is processing a sorted array faster than an unsorted array?
 * 
 * @author madding.lip Jun 4, 2014 9:15:07 AM
 */
public class Test_Sorted_UnSorted_Array {

    public static void main(String[] args) {
        // Generate data
        int arraySize = 32768;
        int data[] = new int[arraySize];

        Random rnd = new Random(0);
        for (int c = 0; c < arraySize; ++c)
            data[c] = rnd.nextInt() % 256;

        // !!! With this, the next loop runs faster
        Arrays.sort(data);

        // Test
        long start = System.nanoTime();
        long sum = 0;

        for (int i = 0; i < 100000; ++i) {
            // Primary loop
            for (int c = 0; c < arraySize; ++c) {
                if (data[c] >= 128) sum += data[c];
            }
        }

        System.out.println((System.nanoTime() - start) / 1000000000.0);
        System.out.println("sum = " + sum);
    }
}

/**
 * 
 * with sort: 
 *      1.542312887
 *      sum = 155184200000
 *  
 * without sort:
 *      6.348332176
 *      sum = 155184200000
 *  
 * subject: 
 *      Why is processing a sorted array faster than an unsorted array? 
 * reaon: 
 *      if (data[c] >= 128) sum += data[c];
 *      处理器的分支预测导致cpu耗时
 *  
 * hack解决： 
 *      int t = (data[c] - 128) >> 31;
 *      sum += ~t & data[c];
 **/
