/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.charset;

/**
 * 类Charset.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Nov 17, 2013 8:07:21 PM
 */
public enum Charset {
    UTF_8("UTF-8"),
    GBK("GBK"), 
    GB2312("GB2312");

    private String charset;

    private Charset(String charset){
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }
}
