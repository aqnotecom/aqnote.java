/*
 * Copyright (C) 2013-2016 Peng Li<aqnote@qq.com>.
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
 * @author "Peng Li"<aqnote@qq.com>
 */
public class MetaClassLoader extends ClassLoader {

    public Class<?> findClass(String classpath) {

        File file = new File(classpath);
        if (!file.exists()) {
            return null;
        }

        byte[] classData = null;
        FileInputStream f = null;
        try {
            f = new FileInputStream(classpath);
            int num = f.available();
            classData = new byte[num];
            f.read(classData);
            f.close();
        } catch (IOException e) {
            System.out.println(e);
        } finally {

        }

        return defineClass(file.getName(), classData, 0, classData.length);
    }
}
