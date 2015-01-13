/*
 * Copyright madding.me.
 */
package com.madding.shared.hadoop.offer.join;

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