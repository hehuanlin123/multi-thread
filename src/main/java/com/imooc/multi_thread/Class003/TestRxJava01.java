package com.imooc.multi_thread.Class003;

public class TestRxJava01 {

    private static Object resourceA = new Object();
    private static Object resourceB = new Object();
    private static String THREAD_A = "thread_a";
    private static String THREAD_B = "thread_b";

    public static void main(String[] args){
        //创建线程A
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA){
                    System.out.println(Thread.currentThread() + " get resourceA");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread() + " waiting get resourceB");

                    synchronized (resourceB){
                        System.out.println(Thread.currentThread() + " get resourceB");
                    }
                }
                ;
            }
        },THREAD_A);

        //创建线程B
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceB){
                    System.out.println(Thread.currentThread() + " get resourceB");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread() + " waiting get resourceA");

                    synchronized (resourceA){
                        System.out.println(Thread.currentThread() + " get resourceA");
                    }
                }
                ;
            }
        },THREAD_B);

        threadA.start();
        threadB.start();
    }
}
