package com.imooc.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.util.StringTokenizer;

public class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Mapper.Context context)
            throws java.io.IOException, InterruptedException {
        String line = value.toString();
        //StringTokenizer默认按照空格来切
        StringTokenizer st = new StringTokenizer(line);
        while (st.hasMoreTokens()) {
            String world = st.nextToken();
            //map输出
            context.write(new Text(world), new IntWritable(1));
        }
    }
}
