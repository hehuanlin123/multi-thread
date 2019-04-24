package com.imooc.multi_thread.Class008;

import java.util.concurrent.CountDownLatch;

/**
 * 应用场景：在主线程中需要开启多个线程去并行执行任务，主线程需要等待所有子线程执行完毕后再进行汇总
 * 1.有两个子线程，所以构造函数参数传递2
 * 2.主线程调用countDownLatch.await()会被阻塞，子线程调用countDownLatch.countDown()
 * 会将计数器减1
 * 3.当计数器为0时，主线程的await()方法才会返回
 */

public class JoinCountDownLatch {

    //创建一个CountDownLatch实例
    private static volatile CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("child threadOne over!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("child threadTwo over!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        });

        Thread threadThree = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("child threadThree over!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        });

        //启动子线程：三个线程如果sleep时间一致，执行的顺序是随机的
        threadOne.start();
        threadTwo.start();
        threadThree.start();

        System.out.println("wait all child thread over!");

        //等待子线程执行完毕，返回
        countDownLatch.await();
        System.out.println("all child thread over!");
    }
}
