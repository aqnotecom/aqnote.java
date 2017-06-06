/*
 * Copyright (C) 2013-2016 Peng Li<aqnote@qq.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.hash.collision;

import com.aqnote.shared.test.hash.type.JavaAndOracleHashAlgorithm;

/**
 * 罗列一致的hash的种子冲突
 * 
 * @author "Peng Li"<aqnote@qq.com>
 */
public class SeedCollision {

    public static void main(String[] args) {
        JavaAndOracleHashAlgorithm.sameHashcode("Aa", "BB");
    }

}
