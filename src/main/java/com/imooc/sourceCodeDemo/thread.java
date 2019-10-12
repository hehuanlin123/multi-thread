package com.imooc.sourceCodeDemo;

import java.util.concurrent.*;

public class thread {

    Thread thread;

    Future future;

    FutureTask futureTask;

    Callable callable;

    ExecutorService executorService;

    ScheduledExecutorService scheduledExecutorService;

    ThreadFactory threadFactory;

    ThreadPoolExecutor threadPoolExecutor;

    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    ThreadLocal threadLocal;

    public static void main(String[] args) {

        Executors.newCachedThreadPool();

    }
}
