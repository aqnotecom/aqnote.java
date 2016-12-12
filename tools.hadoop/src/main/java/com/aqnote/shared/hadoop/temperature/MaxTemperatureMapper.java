/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.hadoop.temperature;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * MaxTemperatureMapper类描述：mapper类
 * 
 * @author madding.lip
 */
public class MaxTemperatureMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, DoubleWritable> {

    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, DoubleWritable> output,
                    Reporter reporter) throws IOException {
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

        output.collect(new Text(year), new DoubleWritable(airTemperature));
    }
}

/**
 
 */
