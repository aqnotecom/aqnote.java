/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.hadoop.offer.merge;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.SequenceFileInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 类MemberOfferCount.java的实现描述：merge中间结果分析
 * @author madding.lip Jun 26, 2012 11:26:26 AM
 */
public class MergeMiddleData extends Configured implements Tool {

    public static class MapClass extends MapReduceBase implements Mapper<LongWritable, BytesWritable, Text, IntWritable> {
        
        private final static IntWritable uno           = new IntWritable(1);
        

        public void map(LongWritable key, BytesWritable value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            byte[] b = value.getBytes();
            String v = new String(b, "UTF-8");
            v.replace(String.valueOf((char)5), ";");

            output.collect(new Text(v), uno);
        }
    }
    
    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterator<IntWritable> values,
                           OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            int count = 0;
            while (values.hasNext()) {
                count += values.next().get();
            }
            output.collect(key, new IntWritable(count));
        }
    }
    
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();

        JobConf job = new JobConf(conf, MergeMiddleData.class);

        Path in = new Path(args[0]);
        Path out = new Path(args[1]);
        FileInputFormat.setInputPaths(job, in);
        FileOutputFormat.setOutputPath(job, out);

        job.setJobName(MergeMiddleData.class.getSimpleName());
        job.setMapperClass(MapClass.class);
        job.setReducerClass(Reduce.class);
//        InputFormat.class;
        job.setInputFormat(SequenceFileInputFormat.class);
        job.setOutputFormat(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        JobClient.runJob(job);

        return 0;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new MergeMiddleData(), args);
        System.exit(res);
    }
    
//    public static void main(String[] args) throws Exception {
//        StringTokenizer itr = new StringTokenizer("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40", String.valueOf(","));
//        int i=0;
//        while (itr.hasMoreTokens()) {
//            String memberId = itr.nextToken().toLowerCase();
//            i++;
//            if(i==37) {
//                System.out.println(memberId);
//                break;
//            }
//            
//        }
//    }
}
