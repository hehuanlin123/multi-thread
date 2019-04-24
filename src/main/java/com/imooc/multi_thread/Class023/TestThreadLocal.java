package com.imooc.multi_thread.Class023;

/**
 * 1.ThreadLocal是JDK包提供的，它提供了线程本地变量，也就是说如果你创建一个ThreadLocal变量，
 * 那么访问这个变量的每一个线程都会有这个变量的一个本地拷贝，从而避免了线程安全问题。
 * 2.ThreadLocal无法解决共享变量更新的问题，并建议使用static修饰。
 * 3.thread1和thread2分别管理自己的本地变量，彼此是访问不了对方的变量的。
 * 4.每个线程中保存的是自己本地内存中的ThreadLocal变量值，多个线程访问的根本就不是共享变量。
 * 5.如果没有使用static修饰，则每创建一个TestThreadLocal实例，内部都会创建一个ThreadLocal实例，
 * 如果使用static修饰，则所有的TestThreadLocal实例共享同一个ThreadLocal实例。
 */
public class TestThreadLocal {

    //（1）创建线程变量
    public static ThreadLocal<String> threadLocal = new InheritableThreadLocal<String>();

    public static void main(String[] args) throws InterruptedException {

        Thread.currentThread().setName("thread1");
        //（2）设置线程变量
        threadLocal.set("String1");
        //（3）启动子线程
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("String2");
                //（4）子线程输出线程变量的值,清除线程变量
                System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
                threadLocal.remove();
            }
        },"thread2");
        thread.start();
        thread.join();
        //（5）主线程输出线程变量的值
        System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        //（6）main线程清除线程变量
        threadLocal.remove();
    }
}
