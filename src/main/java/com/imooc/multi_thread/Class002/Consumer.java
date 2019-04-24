package com.imooc.multi_thread.Class002;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 生产消费者模型
 * 应用场景：发布订阅特定数量的消息
 */
public class Consumer {

    static final int MAX_SIZE = 10;

    //注意这里使用的并发安全的队列不是必须的，这里只是为了展示如何使用wait/notify实现生产消费模型
    static LinkedBlockingDeque<String> queue = new LinkedBlockingDeque<String>();

    public static void main(String[] args){

        //消费线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        //获取共享变量的监视器，一般使用synchronized块
                        synchronized (queue) {
                            while(queue.size() == 0){
                                try{//挂起当前线程，并释放通过同步块获取的queue上面的锁
                                    queue.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            //从队列取出元素，并通知阻塞的生产线程
                            System.out.println(Thread.currentThread().getName() + " take ele " + queue.take());
                            queue.notifyAll();
                        }
                        Thread.sleep(1000);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        },"consumer-thread").start();

        //生产线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        synchronized (queue) {
                            while(queue.size() == MAX_SIZE){
                                try{//挂起当前线程，并释放通过同步块获取的queue上面的锁
                                    queue.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            //空闲则添加元素，并通知阻塞的消费线程
                            int ele = new Random().nextInt(100);
                            queue.add(ele + "");
                            System.out.println(Thread.currentThread().getName() + " add " + queue.take());
                            queue.notifyAll();
                        }
                        Thread.sleep(1000);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        },"provider-thread").start();
    }
}
