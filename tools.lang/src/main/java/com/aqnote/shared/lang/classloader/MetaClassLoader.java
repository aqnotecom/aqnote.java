/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.lang.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义的classloader，加载指定文件为Class对象
 * 
 * @author madding.lip
 */
public class MetaClassLoader extends ClassLoader {

    public Class<?> findClass(String classpath) {

        File file = new File(classpath);
        if (!file.exists()) {
            return null;
        }

        byte[] classData = null;
        try {
            FileInputStream f = new FileInputStream(classpath);
            int num = f.available();
            classData = new byte[num];
            f.read(classData);
            f.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        return defineClass(file.getName(), classData, 0, classData.length);
    }
}
