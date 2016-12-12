/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.hadoop.offer.join;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 类E.java的实现描述：TODO 类实现描述 
 * @author madding.lip Jul 27, 2012 3:08:16 PM
 */
public class JoinReduce extends Reducer<TextPair, Text, Text, Text> {

    public void reduce(TextPair key, Iterable<Text> values, Context context) throws IOException,
                                                                               InterruptedException {
        Text pid = key.getFirst();
        String desc = values.iterator().next().toString();
        while (values.iterator().hasNext()) {
            context.write(pid, new Text(values.iterator().next().toString() + "\t" + desc));
        }
    }
}