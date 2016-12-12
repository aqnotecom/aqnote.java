/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.hadoop.offer;

import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;



/**
 * 类OfferPricerange.java的实现描述：offer价格间隙分析
 * @author madding.lip Jun 26, 2012 11:26:26 AM
 */
public class OfferPricerange {

    public static class MyMapper extends Mapper<Object, Text, Text, Text> {

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            
            String[] record = value.toString().split(" ");
            if(record.length >= 4) {
                
                double maxprice = 0.0;
                try{
                    maxprice = Double.parseDouble(record[3]);
                } catch(NumberFormatException nfe) {
                    System.err.println(value.toString());
                    maxprice = -1;
                }
                double minprice = 0.0;
                try {
                    minprice = Double.parseDouble(record[2]);
                } catch(NumberFormatException nfe) {
                    System.err.println(value.toString());
                    minprice = -1;
                }
                context.write(new Text(value), new Text(new DecimalFormat("0.000000").format(maxprice - minprice)));
            }
        }
    }

    public static class MyReducer extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException,
                                                                                   InterruptedException {
            Text value = new Text();
            for (Text val : values) {
                value = val;
            }

            context.write(key, value);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: wordcount <in> <out>");
            System.exit(2);
        }
        Job job = new Job(conf, "word count");
        job.setJarByClass(OfferPricerange.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
    
}
