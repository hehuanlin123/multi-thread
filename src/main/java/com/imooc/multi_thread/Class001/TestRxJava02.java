package com.imooc.multi_thread.Class001;

public class TestRxJava02 {

    private static final String THREAD_NUM = "sub-thread";

    static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run(){

            System.out.println("--" + Thread.currentThread().getName() + "--");

            try {
                Thread.sleep(200000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){

        MyThread thread = new MyThread(THREAD_NUM);

        thread.start();

        System.out.println("--" + Thread.currentThread().getName() + "--");

        try {
            Thread.sleep(200000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
