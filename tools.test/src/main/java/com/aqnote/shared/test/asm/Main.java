/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 程序入口
 * @author lixin
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		String className = "/Users/madding/scode/github/aqnote/java-tools/tools.test/src/main/resources/class/com/alibaba/normandie/user/querycondition/QueryCondition.class";
		InputStream in = // Class.class.getResourceAsStream(className);
		new FileInputStream(new File(className));
		ClassParser parser = new ClassParser(in);
		parser.parse();
		
	}
}