package com.imooc.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;

import static org.apache.hadoop.mapred.FileInputFormat.*;

public class WordMRDemo {
    public static void main(String[] args) {
        Configuration conf = new Configuration();
        //设置mapper的配置，既就是hadoop/conf/mapred-site.xml的配置信息
        conf.set("mapred.job.tracker", "hadoop:9000");
        try {
            //新建一个Job工作
            Job job = new Job(conf);
            //设置运行类
            job.setJarByClass(WordMRDemo.class);
            //设置要执行的mapper类
            job.setMapperClass(WordMapper.class);
            //设置要执行的reduce类
            job.setReducerClass(WordReduce.class);
            //设置输出key的类型
            job.setMapOutputKeyClass(Text.class);
            //设置输出value的类型
            job.setMapOutputValueClass(IntWritable.class);
            //设置ruduce任务的个数，默认个数为一个（一般reduce的个数越多效率越高）
            //job.setNumReduceTasks(2);
            //mapreduce 输入数据的文件/目录,注意，这里可以输入的是目录。
            FileInputFormat.addInputPath((JobConf) conf, new Path("/Users/szkfzx/DeepLearning/AiLearning/data/15.BigData_MapReduce"));
            //mapreduce 执行后输出的数据目录，不能预先存在，否则会报错。
            FileOutputFormat.setOutputPath((JobConf) conf, new Path("/Users/szkfzx/DeepLearning/AiLearning/data/15.BigData_MapReduce/out"));
            //执行完毕退出
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}