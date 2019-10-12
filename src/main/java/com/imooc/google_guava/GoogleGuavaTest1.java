package com.imooc.google_guava;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * guava缓存测试类
 * @create 2019-04-10 下午4:37
 */
public class GoogleGuavaTest1 {
    //maximumSize 设置缓存大小，expireAfterAccess 设置超时时间 5毫秒（5毫秒没人访问就设置缓存失效） （方法二同
    static LoadingCache<String,String> userNameCache =
            CacheBuilder.newBuilder().maximumSize(100).
                    expireAfterAccess(5000, TimeUnit.MILLISECONDS).build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    Random r = new Random();
                    int i = r.nextInt(100);
                    return "你好，我是"+s+"，编号："+i;
                }
            });

    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in);
        Boolean flag = true;
        while(flag) {
            System.out.println("请输入一个名字：");
            String next = input.next();
            String s = userNameCache.get(next);
            System.out.println(s);
            System.out.println("是否继续？y/n");
            next = input.next();
            if(!next.equalsIgnoreCase("y")){
                flag = false;
            }
        }
    }
}

