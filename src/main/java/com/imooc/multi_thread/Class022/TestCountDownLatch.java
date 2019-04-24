package com.imooc.multi_thread.Class022;

import java.util.concurrent.CountDownLatch;

/**
 * 使用CountDownLatch，要注意countDown()方法要在finally块内执行，避免抛异常后得不到执行。
 */
public class TestCountDownLatch {

    //计数器初始化为2
    private static volatile CountDownLatch countDownLatch = new CountDownLatch(2);

    //开启两个线程
    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("child threadOne over!");
                    throw new RuntimeException("error");
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

        //启动子线程
        threadOne.start();
        threadTwo.start();

        System.out.println("wait all child thread over!");

        //等待子线程执行完毕，返回
        //main线程挂起，直到计数器为0，才返回
        countDownLatch.await();

        System.out.println("all child thread over!");
    }
}
