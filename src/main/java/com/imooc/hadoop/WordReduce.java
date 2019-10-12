package com.imooc.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> iterator, Context context)
            throws java.io.IOException ,InterruptedException {
        int sum = 0 ;
        for(IntWritable i:iterator){
            sum+=i.get();
        }
        context.write(key, new IntWritable(sum));
    }
}
