/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.hadoop.offer.join;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * 类Join.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Jul 27, 2012 3:00:18 PM
 */
public class Join {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        
        args = new String[3];
        
        args[0] = "/home/madding/hadoop/input/data.txt";
        args[1] = "/home/madding/hadoop/input/info.txt";
        args[2] = "/home/madding/hadoop/output";
        
        Configuration conf = new Configuration();
        GenericOptionsParser parser = new GenericOptionsParser(conf, args);
        String[] otherArgs = parser.getRemainingArgs();
        if (args.length < 3) {
            System.err.println("Usage: join <in_path_one> <in_path_two> <output>");
            System.exit(2);
        }
        // conf.set("hadoop.job.ugi", "root,hadoop");
        Job job = new Job(conf, "join");
        // 设置运行的job
        job.setJarByClass(Join.class);
        // 设置Map相关内容
        job.setMapperClass(JoinMapper.class);
        // 设置Map的输出
        job.setMapOutputKeyClass(TextPair.class);
        job.setMapOutputValueClass(Text.class);
        // 设置partition
        job.setPartitionerClass(JoinPartitioner.class);
        // 在分区之后按照指定的条件分组
        job.setGroupingComparatorClass(JoinComparator.class);
        // 设置reduce
        job.setReducerClass(JoinReduce.class);
        // 设置reduce的输出
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        // 设置输入和输出的目录
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
        // 执行，直到结束就退出
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}









