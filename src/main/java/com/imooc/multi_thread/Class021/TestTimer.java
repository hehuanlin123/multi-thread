package com.imooc.multi_thread.Class021;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer内部当执行任务的过程中抛出了除InterruptedException之外的异常后，
 * 唯一的消费线程就会因为抛出异常而终止，队列里面执行的其他任务就会被终止。
 */
public class TestTimer {

    //创建定时器对象
    static Timer timer = new Timer();

    public static void main(String[] args) {
        //添加任务1，延迟500ms执行
        timer.schedule(new TimerTask() {
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
        }, 500);

        //添加任务1，延迟1000ms执行
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (;;) {
                    System.out.println("---two Task---");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000);
    }
}
