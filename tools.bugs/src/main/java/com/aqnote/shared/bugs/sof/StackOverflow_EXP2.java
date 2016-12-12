/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.bugs.sof;

/**
 * StackOverflow.java desc：TODO
 * 
 * @author madding.lip Jun 10, 2014 4:19:43 PM
 */
public class StackOverflow_EXP2 {

    public static void main(String[] args) {
//        try {
//            simulation(1, 5);
//        } catch (Exception ex) {
//            System.out.println(ex.toString());
//        }
        
        noStackOverflow(0);
    }

    public static void simulation(int n, int limit) throws Exception {
        try { // simulate a depth limited call stack
            printTab(n);
            System.out.println(n + " - Try");
            if (n < limit) simulation(n + 1, limit);
            else throw new Exception("StackOverflow@try(" + n + ")");
        } finally {
            printTab(n);
            System.out.println(n + " - Finally");
            if (n < limit) simulation(n + 1, limit);
            else throw new Exception("StackOverflow@finally(" + n + ")");
        }
    }

    public static void noStackOverflow(int x) {
        System.out.println("function " + x);
        try {
            noStackOverflow(x + 1);
        } finally {
            System.out.println(" finally " + x);
            noStackOverflow(x + 1);
        }
    }

    public static void stackOverflow() {
        try {
            stackOverflow();
        } finally {
            stackOverflow();
        }
    }

    public static void bar() {
        bar();
    }

    private static void printTab(int n) {
        int i = 0;
        while (++i < n) {
            System.out.print("  ");
        }
    }
}

/**

 */
