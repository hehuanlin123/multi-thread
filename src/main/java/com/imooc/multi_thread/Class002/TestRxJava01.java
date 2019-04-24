package com.imooc.multi_thread.Class002;

public class TestRxJava01 {

    public static void main(String[] args) throws InterruptedException {

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;){
                    System.out.println("生产螺丝，并放入自己的仓库");
                }
            }
        },"螺丝生产线程");

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;){
                    System.out.println("生产螺母，并放入自己的仓库");
                }
            }
        },"螺母生产线程");

        threadA.start();

        threadB.start();
    }
}
