/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * TestChar.java desc：TODO
 * 
 * @author madding.lip May 4, 2014 2:37:45 PM
 */
public class ClassLoaderTest {

	public static void main(String[] args) throws MalformedURLException {

		// 输出bootstrapclassloader默认配置加载路径
		System.out.println(System.getProperty("sun.boot.class.path"));
		System.out.println("===========================================");

		// 输出bootstrapclassloader默认加载的路径
//		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
//		for (int i = 0; i < urls.length; i++) {
//			System.out.println(urls[i].toExternalForm());
//		}
//		System.out.println("===========================================");

		// 获得加载ClassLoaderTest.class这个类的类加载器
		ClassLoader loader = ClassLoaderTest.class.getClassLoader();
		while (loader != null) {
			System.out.println(loader);
			loader = loader.getParent();
		}
		System.out.println(loader);
		System.out.println("===========================================");
		
		// 获取new出来的classloader的继承关系
		String path = "file://Users/madding/Jars";
		final URL[] array = new URL[]{new URL(path)};
		loader = new URLClassLoader(array);
		while (loader != null) {
			System.out.println(loader);
			loader = loader.getParent();
		}
		System.out.println(loader);
		System.out.println("===========================================");
		
	}
}