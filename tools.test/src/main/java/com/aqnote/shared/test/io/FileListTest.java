/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.io;

import java.io.File;

public class FileListTest {  
    
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        String[] files = new File("/home/madding/output/test/WEB-INF/lib").list();
        for(String str: files){  
            System.out.println(str);  
        }  
    }  
}  

/**
 * 结论：
 * linux：乱序,但同一台机器的运行结果是一致
 * windows：ascii顺序
 * 
 */
