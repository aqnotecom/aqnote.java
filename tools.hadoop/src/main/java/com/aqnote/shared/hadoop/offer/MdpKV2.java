/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.hadoop.offer;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqnote.shared.hadoop.offer.util.MdpParseUtil;

/**
 * 类MdpKV.java的实现描述：mdf价格区间解析
 * 
 * @author madding.lip Jun 28, 2012 1:17:15 PM
 */
public class MdpKV2 extends Configured implements Tool {
    
    private static final Logger logger = LoggerFactory.getLogger(MdpKV2.class);

    public static class MapClass extends Mapper<BytesWritable, Text, LongWritable, Text> {

        public void map(BytesWritable key, Text value, Context context) throws IOException, InterruptedException {
            
            String v = value.toString();
//                new String(value.getBytes(),0, value.getLength(), "UTF-8");
            String[] vlist = v.split(String.valueOf((char) 5));
            if (vlist.length >= 66) {
                String mdp = vlist[65];
                long offerId = -1;
                try {
                    offerId = Long.parseLong(vlist[0]);
                    Double[] pricerange = null;
                    
                    try {
                        pricerange = MdpParseUtil.getPricerange(mdp);
                    } catch (Exception e) {
                        logger.error("mdp dom error: ", e);
                    }
                    
                    if (pricerange != null) {
                        context.write(new LongWritable(offerId), new Text(pricerange[0] + "," + pricerange[1]));
                    }
                } catch (NumberFormatException nfe) {
                    logger.error("format offerId error: " + nfe);
                }
            }
        }
    }

    public static class Reduce extends Reducer<LongWritable, Text, LongWritable, Text> {

        public void reduce(LongWritable key, Iterator<Text> values, Context context) throws IOException,
                                                                                    InterruptedException {
            String pricerange = null;
            while (values.hasNext()) {
                pricerange += values.next().toString() + ";";
            }
            context.write(key, new Text(pricerange));
        }
    }

    public int run(String[] args) throws Exception {
        Configuration conf = getConf();

        Job job = new Job(conf, MdpKV2.class.getSimpleName());

        Path in = new Path(args[0]);
        Path out = new Path(args[1]);
        FileInputFormat.setInputPaths(job, in);
        FileOutputFormat.setOutputPath(job, out);

        job.setMapperClass(MapClass.class);
        job.setReducerClass(Reduce.class);
        job.setInputFormatClass(SequenceFileInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 1 : 0);
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new MdpKV2(), args);
        System.exit(res);
    }

}
