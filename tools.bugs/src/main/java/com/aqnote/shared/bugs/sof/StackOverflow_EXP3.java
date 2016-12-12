/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.bugs.sof;

public class StackOverflow_EXP3 {

    private static int level = 0;
    
    public static long fact(int n) {
        level++; 
        return n < 2 ? 1 : fact(n - 1);
    }

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(null, null, "TT", 1024 * 1) {
            private int total_count = 1 << 16;

            @Override
            public void run() {
                try {
                    level = 0;
                    System.out.println(fact(total_count));
                } catch (StackOverflowError e) {
                    System.out.println("total recursion level is " + total_count);
                    System.err.println("true recursion level was " + level);
                    System.err.println("reported recursion level was " + e.getStackTrace().length);
//                    e.printStackTrace();
                }
            }

        };
        t.start();
        t.join();
    }
}

/**
 * 测试结果数据：
 * 
 * Memory   recursion level              stackTrace length
 * 1k        729                         731
 * 2k        729                         731
 * 4k        729                         731
 * 8k        729                         731
 * 16k       729                         731
 * 32k       729                         731
 * 64k       729                         731
 * 128k      729                         731
 * 256k      1893                        1024
 * 512K      4871                        1024
 * 1M        10829                       1024
 * 2M        22745                       1024
 * 
 * 归纳：
 * 1.java中栈空间保存最多1024
 * 2.Xss（线程栈大小）直接限制了递归深度，程序中的内容主要是function指针，如果function中包含跟多的业务逻辑，其递归量将远远低于这个值；可通过添加原子类型在模拟
 * 3.。。。。
 * 
 */