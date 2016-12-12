/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.hadoop.offer.join;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 类E.java的实现描述：TODO 类实现描述 
 * @author madding.lip Jul 27, 2012 3:09:55 PM
 */
public class JoinPartitioner extends Partitioner<TextPair, Text> {

    @Override
    public int getPartition(TextPair key, Text value, int numParititon) {
        return Math.abs(key.getFirst().hashCode() * 127) % numParititon;
    }
}