/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.serializer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;


/**
 * 类HessianTest.java的实现描述：TODO 类实现描述 
 * @author madding.lip Sep 2, 2013 1:36:35 PM
 */
public class HessianSerializerTest {
    
    public static void main(String[] args) throws Exception {
        
        MemberDO memberDo = new MemberDO();
        memberDo.setId(123l);

        String file = "test.obj";
        OutputStream os = new FileOutputStream(file);
        HessianOutput out = new HessianOutput(os);

        out.writeObject(memberDo);
        os.close();
        
        InputStream is = new FileInputStream(file);
        HessianInput in = new HessianInput(is);

        Object obj = in.readObject(null);
        is.close();
        
        System.out.println(obj);
    }

}
