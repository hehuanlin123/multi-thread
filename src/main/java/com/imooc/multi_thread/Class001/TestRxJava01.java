package com.imooc.multi_thread.Class001;

public class TestRxJava01 {

    private static final String THREAD_NUM = "sub-thread";

    public static void main(String[] args){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("--" + Thread.currentThread().getName() + "--");

                try {
                    Thread.sleep(200000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },THREAD_NUM);

        //创建守护线程
        thread.setDaemon(true);

        thread.start();

        System.out.println("--" + Thread.currentThread().getName() + "--");

        try {
            Thread.sleep(200000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
