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
public class StackOverflow_EXP1 {

    public static void main(String[] args) {
        main(args);
    }
}

/**
 Exception in thread "main" java.lang.StackOverflowError
    at com.aqnote.shared.bugs.sof.StackOverflow.main(StackOverflow.java:16)
    at com.aqnote.shared.bugs.sof.StackOverflow.main(StackOverflow.java:16)
    at com.aqnote.shared.bugs.sof.StackOverflow.main(StackOverflow.java:16)
    at com.aqnote.shared.bugs.sof.StackOverflow.main(StackOverflow.java:16)
 */
