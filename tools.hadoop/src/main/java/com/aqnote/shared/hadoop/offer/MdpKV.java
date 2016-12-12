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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqnote.shared.hadoop.offer.util.MdpParseUtil;

/**
 * 类MdpKV.java的实现描述：mdf价格区间解析
 * 
 * @author madding.lip Jun 28, 2012 1:17:15 PM
 */
public class MdpKV extends Configured implements Tool {

    private static final Logger logger = LoggerFactory.getLogger(MdpKV.class);

    public static class MapClass extends MapReduceBase implements Mapper<BytesWritable, Text, Text, Text> {

        public void map(BytesWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
                                                                                                                     throws IOException {
            String v = value.toString();
            // new String(value.getBytes(),0, value.getLength(), "UTF-8");
            String[] vlist = v.split(String.valueOf((char) 5));
            if (vlist.length >= 66) {
                String memberId = vlist[36];
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
                        output.collect(new Text(memberId + "," + offerId), new Text(pricerange[0] + "," + pricerange[1]));
                    }
                } catch (NumberFormatException nfe) {
                    logger.error("format offerId error: " + nfe);
                }
            }
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output,
                           Reporter reporter) throws IOException {
            String pricerange = null;
            while (values.hasNext()) {
                if (pricerange == null) {
                    pricerange = values.next().toString() + ";";
                } else {
                    pricerange += values.next().toString() + ";";
                }
            }
            output.collect(key, new Text(pricerange));
        }
    }

    public int run(String[] args) throws Exception {
        Configuration conf = getConf();

        JobConf job = new JobConf(conf, MdpKV.class);

        Path in = new Path(args[0]);
        Path out = new Path(args[1]);
        FileInputFormat.setInputPaths(job, in);
        FileOutputFormat.setOutputPath(job, out);

        job.setJobName(MdpKV.class.getSimpleName());
        job.setMapperClass(MapClass.class);
        job.setReducerClass(Reduce.class);
        job.setInputFormat(SequenceFileInputFormat.class);
        job.setOutputFormat(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        JobClient.runJob(job);
        return 0;
    }

    public static void main(String[] args) throws Exception {
        printUsage();

        Configuration conf = new Configuration();

        int res = ToolRunner.run(conf, new MdpKV(), args);
        System.exit(res);
    }

    public static void printUsage() {
        ToolRunner.printGenericCommandUsage(System.out);
    }

}
