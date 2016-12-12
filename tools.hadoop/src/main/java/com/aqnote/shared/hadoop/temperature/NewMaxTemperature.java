/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.hadoop.temperature;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class NewMaxTemperature {

    static class NewMaxTemperatureMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (line.length() < 100) {
                return;
            }

            String year = line.substring(13, 17);
            try {
                Integer.parseInt(year);
            } catch (NumberFormatException e) {
                return;
            }

            String airTemperatureStr = line.substring(85, 90);
            if ("*****".equals(airTemperatureStr)) {
                return;
            }

            double airTemperature;

            try {
                airTemperature = Double.parseDouble(airTemperatureStr);
            } catch (Exception e) {
                return;
            }

            context.write(new Text(year), new DoubleWritable(airTemperature));
        }
    }

    static class NewMaxTemperatureReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException,
                                                                                       InterruptedException {
            double maxValue = Double.MIN_VALUE;
            for (DoubleWritable value : values) {
                maxValue = Math.max(maxValue, value.get());
            }
            context.write(key, new DoubleWritable(maxValue));
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: NewMaxTemperature <input path> <output path>");
            System.exit(-1);
        }

        Job job = new Job();
        job.setJarByClass(NewMaxTemperature.class);
        job.setJobName("compute max temperature");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(NewMaxTemperatureMapper.class);
        job.setReducerClass(NewMaxTemperatureReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
