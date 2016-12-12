/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.asm;

import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

	public static int read1(InputStream in) throws IOException {
		return in.read() & 0xff;
	}

	public static int read2(InputStream in) throws IOException{
		return (read1(in) << 8) | read1(in);
	}

	public static int read4(InputStream in) throws IOException {
		return (read2(in) <<16) | read2(in);
	}

	public static long read8(InputStream in) throws IOException {
		long high = read4(in) & 0xffffffffl;
		long low  = read4(in) & 0xffffffffl;
		return (high << 32) | (low);
	}

	public static byte[] read(InputStream in,int length) throws IOException {
		byte[] buf = new byte[length];
		in.read(buf, 0, length);
		return buf;
	}
}