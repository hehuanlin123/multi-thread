package com.imooc.multi_thread.Class015;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程池时要指定有意义的名称，以便问题追溯
 */
public class MeaningfulThreadPoolName {

    /*static ThreadPoolExecutor executorOne = new ThreadPoolExecutor(5,5,1, TimeUnit.MINUTES,new LinkedBlockingDeque<Runnable>());
    static ThreadPoolExecutor executorTwo = new ThreadPoolExecutor(5,5,1, TimeUnit.MINUTES,new LinkedBlockingDeque<Runnable>());*/

    static ThreadPoolExecutor executorOne = new ThreadPoolExecutor(5,5,1,
            TimeUnit.MINUTES,new LinkedBlockingDeque<Runnable>(),
            new NamedThreadFactory(""));
    static ThreadPoolExecutor executorTwo = new ThreadPoolExecutor(5,5,1,
            TimeUnit.MINUTES,new LinkedBlockingDeque<Runnable>(),
            new NamedThreadFactory("ASYN-PROCESS-POOL"));
    static ThreadPoolExecutor executorThree = new ThreadPoolExecutor(5,5,1,
            TimeUnit.MINUTES,new LinkedBlockingDeque<Runnable>(),
            new NamedThreadFactory("ASYN-PROCESS-POOL"));

    public static void main(String[] args) {

        //接受用户链接模块
        executorOne.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("接受用户链接线程");
                //throw new NullPointerException();
                //Exception in thread "pool-1-thread-1"
            }
        });

        //具体处理用户请求模块
        executorTwo.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("具体处理用户请求线程");
                //throw new NullPointerException();
                //Exception in thread "ASYN-PROCESS-POOL-2-thread-2"
            }
        });

        //测试模块
        executorThree.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试模块");
                throw new NullPointerException();
            }
        });

        executorOne.shutdown();
        executorTwo.shutdown();
        executorThree.shutdown();
    }
}
