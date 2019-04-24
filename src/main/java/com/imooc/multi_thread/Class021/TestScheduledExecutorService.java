package com.imooc.multi_thread.Class021;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Timer下启动多个任务，只要其中一个任务抛出异常，其他任务会自动终止，
 * ScheduledExecutorService不会有这个风险。
 */
public class TestScheduledExecutorService {

    static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    public static void main(String[] args) {
        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("---one Task---");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("error");
            }
        },500, TimeUnit.MILLISECONDS);

        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < 20;++i) {
                    System.out.println("---two Task---");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },1000, TimeUnit.MILLISECONDS);

        scheduledThreadPoolExecutor.shutdown();
    }
}
