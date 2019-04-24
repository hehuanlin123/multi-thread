package com.imooc.multi_thread.Class008;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.setState;
import static sun.misc.VM.getState;

public class CountDownLatch {

    /**
     * AQS
     */
    private static final class Sync extends AbstractQueuedSynchronizer{

        private static final long serialVersionUID = 5944458472153693623L;

        Sync(int count){
            setState(count);
        }

        int getCount(){
            return getState();
        }

        /**
         * AQS获取共享资源的时候被中断的方法
         * 委托sync调用AQS的acquireSharedInterruptibly方法
         * @param arg
         * @throws InterruptedException
         */
        /*public final void acquireSharedInterruptibly(int arg) throws InterruptedException{
            if(Thread.interrupted()){//如果线程中断则抛出异常
                throw new InterruptedException();
            }
            if(tryAcquireShared(arg) < 0){//尝试看当前计数是否数值为0，为0则直接返回，否则进入AQS队列等待
                doAcquireSharedInterruptibly(arg);
            }
        }*/

        //sync类实现的AQS的接口
        protected int tryAcquireShared(int acquires){
            return (getState() == 0) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int releases){
            for (;;){
                int c = getState();
                if (c == 0){
                    return false;
                }
                int nextc = c - 1;
                if(compareAndSetState(c,nextc)){
                    return nextc == 0;
                }
            }
        }

        /**
         * AQS的方法
         * @param arg
         * @return
         */
        /*public final boolean releaseShared(int arg){
            //调用sync实现的tryReleaseShared
            if(tryReleaseShared(arg)){
                //AQS的释放资源方法
                doReleaseShared();
                return true;
            }
            return false;
        }*/
    }

    //基于AQS的同步器
    private Sync sync;

    /**
     * 当线程调用了CountDownLatch对象的await方法后，当前线程会被阻塞，直到下面情况之一发生时才会返回：
     * （1）当所有线程都调用了CountDownLatch对象的countDown方法后，也就是计时器值为0的时候；
     * （2）其他线程调用了当前线程的interrupt()方法中断当前线程，当前线程会抛出InterruptedException异常后返回。
     * @throws InterruptedException
     */

    //CountDownLatch的await()方法，等待计数为0
    public void await() throws InterruptedException{
        sync.acquireSharedInterruptibly(1);
    }

    //CountDownLatch的countDown()方法，计数递减1
    public void countDown(){
        sync.releaseShared(1);
    }

    public CountDownLatch(int count) {
        if(count < 0){
            throw new IllegalArgumentException("count < 0");
//            this.sync = sync(count);
        }
    }

    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1,unit.toNanos(timeout));
    }

    public long getCount(){
        return sync.getCount();
    }


}
