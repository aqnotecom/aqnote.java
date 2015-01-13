package com.madding.shared.test.io;

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
