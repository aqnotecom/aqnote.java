/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.hash.collision;

import com.aqnote.shared.test.hash.type.JavaAndOracleHashAlgorithm;

/**
 * 罗列一致的hash的种子冲突
 * 
 * @author madding.lip
 */
public class SeedCollision {

    public static void main(String[] args) {
        JavaAndOracleHashAlgorithm.sameHashcode("Aa", "BB");
    }

}
