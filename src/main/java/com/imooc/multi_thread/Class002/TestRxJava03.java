package com.imooc.multi_thread.Class002;

public class TestRxJava03 {

    private static final String THREAD_NUM = "biz-thread";

    public static void main(String[] args) throws InterruptedException {

        //创建线程
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("i am a sub-thread");
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("i am interrupted");

                //使用Interrupted优雅退出的例子
                while(!Thread.currentThread().isInterrupted()){
                    System.out.println(Thread.currentThread() + " hello");
                }
            }
        },THREAD_NUM);
        //启动
        thread.start();
        //主线程（用户线程）休眠3s
        Thread.sleep(3000);
        //中断子线程
        System.out.println("----begin thread interrupt-----");
        thread.interrupt();
        System.out.println("----end thread interrupt-------");
    }
}
