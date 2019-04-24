package com.imooc.multi_thread.Class017;

import java.util.concurrent.*;

/**
 * 不允许使用工具类Executor创建线程池，而是通过手动构造ThreadPoolExecutor来创建
 */
public class newThreadPoolByExecutor {

    public static ExecutorService newFixedThreadPool(int nThreads){
        //keepAliveTime:0 说明只要线程个数多于核心线程个数并且当前空闲就回收
        return new ThreadPoolExecutor(nThreads,nThreads,0L,
                TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>());
    }

    //使用自定义线程创建工厂
    public static ExecutorService newFixedThreadPoolBySelf(int nThreads, ThreadFactory threadFactory){
        return new ThreadPoolExecutor(nThreads,nThreads,0L,
                TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(),threadFactory);
    }

    /*public static ExecutorService newSingleThreadExecutor(){
        return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1,1,0L,
                TimeUnit.MINUTES,new LinkedBlockingDeque<Runnable>()));
    }*/

    //使用自己的线程工厂
    /*public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory){
        return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1,1,0L,
                TimeUnit.MINUTES,new LinkedBlockingDeque<Runnable>(),threadFactory));
    }*/

    public static ExecutorService newCachedThreadPool(){
        //阻塞队列长度为Integer.MAX_VALUE，并且为同步队列
        //keepAliveTime=60说明只要当前线程60s内空闲则回收
        return new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L,
                TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
    }

    //使用自定义线程创建工厂
    public static ExecutorService newCachedThreadPoolBySelf(ThreadFactory threadFactory){
        return new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L,
                TimeUnit.SECONDS,new SynchronousQueue<Runnable>(),threadFactory);
    }
}
