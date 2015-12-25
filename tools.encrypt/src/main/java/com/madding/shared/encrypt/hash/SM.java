/*
 * Programmer-tools -- A develop code for dever to quickly analyse
 * Copyright (C) 2013-2016 madding.lip <madding.lip@gmail.com>.
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation;
 */
package com.madding.shared.encrypt.hash;

import org.bouncycastle.jcajce.provider.digest.SM3;
import org.bouncycastle.util.encoders.Hex;

/**
 * SM.java desc：TODO 
 * @author madding.lip Dec 24, 2015 6:13:41 PM
 */
public class SM {

    private static final String DEFAULT_CHARSET = "UTF-8";
    
    public final static String _sm3(byte[] src) {
        SM3.Digest md = new SM3.Digest();
        md.update(src);
        return new String(Hex.encode(md.digest()));
    }
    
    
}
