/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.serializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 类JavaSerializerTest.java的实现描述：TODO 类实现描述 
 * @author madding.lip Sep 2, 2013 1:36:35 PM
 */
public class JavaSerializerTest {

    /*
    java：
        1. 
        序列化内容：子类实现序列化，父类没实现序列化  
        中间类操作： 无
        反序列化结果：序列化后不存在父类的任何信息
        
        2. 
        序列化内容：子类实现序列化，父类实现序列化
        中间类操作：无
        反序列化结果：序列化后父子信息都存在
        
        3. 
        序列化内容：子类实现序列化，父类实现序列化后
        中间类操作：
        反序列化结果：
    */
    public static void main(String[] args) throws Exception {
        
        MemberDO memberDo = new MemberDO();
        memberDo.setId(123l);
        memberDo.setExt("hh");

        String file = "/home/madding/test.obj";
        
        File f=new File(file);
        if(f.exists()){
            f.delete();
        }
        
        FileOutputStream os=new FileOutputStream(f);
        ObjectOutputStream oos=new ObjectOutputStream(os);
        oos.writeObject(memberDo);
        oos.close();
        os.close();

        InputStream is=new FileInputStream(f);
        ObjectInputStream ois=new ObjectInputStream(is);
        System.out.println(ois.readObject());
        ois.close();
    }
}
