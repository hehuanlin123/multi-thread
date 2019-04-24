package com.imooc.multi_thread.Class015;

/**
 * 创建线程时要指定有意义的名称，以便问题追溯
 */
public class MeaningfulThreadName {

    static final String THREAD_SAVE_ORDER = "THREAD_SAVE_ORDER";

    static final String THREAD_SAVE_ADDR = "THREAD_SAVE_ADDR";

    public static void main(String[] args) {
        // 订单模块
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("保存订单的线程");
                throw new NullPointerException();
            }
        }, THREAD_SAVE_ORDER);

        // 发货模块
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("保存收货地址的线程");
            }
        }, THREAD_SAVE_ADDR);

        threadOne.start();
        threadTwo.start();
    }
}
