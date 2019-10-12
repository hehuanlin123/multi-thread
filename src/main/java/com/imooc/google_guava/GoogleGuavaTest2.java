package com.imooc.google_guava;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * guava缓存测试类
 * @create 2019-04-10 下午4:37
 */
public class GoogleGuavaTest2 {
    static Cache<String,String> userNameCache =
            CacheBuilder.newBuilder().maximumSize(100).
                    expireAfterAccess(5000, TimeUnit.MILLISECONDS).build();

    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in);
        Boolean flag = true;
        while(flag) {
            System.out.println("请输入一个名字：");
            String next = input.next();
            String s = userNameCache.get(next, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Random r = new Random();
                    int i = r.nextInt(100);
                    return "，编号："+i;
                }
            });
            System.out.println("你好，我是"+next+s);
            System.out.println("是否继续？y/n");
            next = input.next();
            if(!next.equalsIgnoreCase("y")){
                flag = false;
            }
        }
    }
}
