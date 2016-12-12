/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.code;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Test {

	private static Map<String, String> objectToKV(Object obj) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Class<? extends Object> clazz = obj.getClass();
			Method[] methods = clazz.getDeclaredMethods();

			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().startsWith("get")) {
					Object value = methods[i].invoke(obj);
					map.put(getFieldName(methods[i].getName()), String.valueOf(value));
				}
			}

		} catch (Throwable e) {
			System.err.println(e);
		}
		return map;
	}

	private static Map<String, String> objectToKVs(Object obj) {
		Map<String, String> map = new HashMap<String, String>();
		Class<? extends Object> clazz = obj.getClass();
		Method[] method = clazz.getDeclaredMethods();
		objectToKV(obj, method, map);

		while (clazz.getSuperclass() != null) {
			clazz = clazz.getSuperclass();
			method = clazz.getDeclaredMethods();
			objectToKV(obj, method, map);
		}
		return map;
	}

	private static void objectToKV(Object obj, Method[] methods, Map<String, String> map) {
		try {
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().startsWith("get") && !methods[i].getName().equalsIgnoreCase("getClass")) {
					Object value = methods[i].invoke(obj);
					map.put(getFieldName(methods[i].getName()), String.valueOf(value));
				}
			}

		} catch (Throwable e) {
			System.err.println(e);
		}
	}

	private static String getFieldName(String methodName) {
		if (methodName == null || "".equalsIgnoreCase(methodName)) {
			return "";
		}
		methodName = methodName.substring(3);
		char header = (char) methodName.charAt(0);
		StringBuilder result = new StringBuilder();
		result.append(Character.toLowerCase(header));
		result.append(methodName.substring(1));
		return result.toString();
	}

	public static void main(String[] args) {

		Test.B a = new Test.B();
		a.setFa1(123);
		a.setFa2("a");
		a.setFa3('1');
		a.setFb1(456);
		a.setFb2("b");
		a.setFb3("xxxx");

		Map<String, String> map = objectToKV(a);
		System.out.println(map);
		map = objectToKVs(a);
		System.out.println(map);
	}

	public static class A {
		public int fa1;
		public String fa2;
		public char fa3;

		public int getFa1() {
			return fa1;
		}

		public void setFa1(int fa1) {
			this.fa1 = fa1;
		}

		public String getFa2() {
			return fa2;
		}

		public void setFa2(String fa2) {
			this.fa2 = fa2;
		}

		public char getFa3() {
			return fa3;
		}

		public void setFa3(char fa3) {
			this.fa3 = fa3;
		}

	}

	public static class B extends A {
		public int fb1;
		public String fb2;
		public String fb3;

		public int getFb1() {
			return fb1;
		}

		public void setFb1(int fb1) {
			this.fb1 = fb1;
		}

		public String getFb2() {
			return fb2;
		}

		public void setFb2(String fb2) {
			this.fb2 = fb2;
		}

		public String getFb3() {
			return fb3;
		}

		public void setFb3(String fb3) {
			this.fb3 = fb3;
		}

	}

}
