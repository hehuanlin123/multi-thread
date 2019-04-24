# 1.案例介绍

- 应用场景：在主线程中需要开启多个线程去并行执行任务，主线程需要等待所有子线程执行完毕后再进行汇总

- 在CountDownLatch出现之前一般都是使用线程的join()方法，但是join不够灵活，不能够满足不同场景的
需要，所以JDK开发了CountDownLatch这个类

# 2.CountDownLatch实现原理
- void await()方法

当线程调用了CountDownLatch对象的await方法后，当前线程会被阻塞，直到下面情况之一发生时才会返回：

（1）当所有线程都调用了CountDownLatch对象的countDown方法后，也就是计时器值为0的时候；

（2）其他线程调用了当前线程的interrupt()方法中断当前线程，当前线程会抛出InterruptedException异常后返回。

- boolean await(long timeout,TimeUnit unit)方法

当线程调用了CountDownLatch对象的await方法后，当前线程会被阻塞，直到下面情况之一发生时才返回；

（1）当所有线程都调用了CountDownLatch对象的countDown方法后，也就是计时器值为0的时候，才会返回true；

（2）设置timeout时间到了，因为超时而返回false；

（3）其他线程调用了当前线程的interrupt()方法中断当前线程，当前线程会抛出InterruptedException异常后返回。

- void countDown()方法

当线程调用了该方法后，会递减计数器的值，递减后，如果计数器为0则会唤醒所有调用await方法而被阻塞的线程，否则什么都不做

- long getCount()方法

获取当前计数器的值，也就是AQS的state的值，一般在debug测试的时候使用