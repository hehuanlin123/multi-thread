package com.imooc.multi_thread.Class004;

import sun.misc.Unsafe;

import java.io.Serializable;

public abstract class AtomicLong extends Number implements Serializable {

    private static final long serialVersionUID = -9096675516785019422L;

    //1.创建unsafe实例
    //AtomicLong中本质是使用Unsafe提供的CAS方法实现计数安全的
    //Unsafe不能直接实例化，只能通过AtomicLong获取实例
    //原因：AtomicLong在JDK的rt.jar包中通过BootstrapClassLoader加载main函数，而自己的main函数是通过AppClassLoader加载
    //解决方法：使用万能的反射可以获取可以使用的Unsafe实例
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;//value在AtomicLong中的偏移量

    static {
        try {//2.获取变量value在AtomicLong中的偏移量，保存到valueOffset中
            valueOffset = unsafe.objectFieldOffset(
                    AtomicLong.class.getDeclaredField("value"));
        } catch(Exception ex) {
            throw new Error(ex);
        }
    }

    //3.计数器值：volatile
    private volatile long value;

    //4.获取计数值
    //去主内存中而不是本地变量中获取value的值
    public final long get(){
        return value;
    }

    //5.设置计数值
    //将本地变量的值刷新到主内存
    public final void set(long newValue){
        value = newValue;
    }

    //6.递增/递减，然后返回递增/递减后的值
    //原子性递增/递减value的值，this是AtomicLong对象的引用
    public final long incrementAndGet(){
        return unsafe.getAndAddLong(this,valueOffset,1L) + 1L;
    }

    public final long decrementAndGet(){
        return unsafe.getAndAddLong(this,valueOffset,-1L) - 1L;
    }

    //7.递增/递减，然后返回递增/递减前的值
    public final long getAndIncrement(){
        return unsafe.getAndAddLong(this,valueOffset,1L);
    }

    public final long getAndDecrement(){
        return unsafe.getAndAddLong(this,valueOffset,-1L);
    }

    //8.CAS替换原子变量的值
    //this是AtomicLong对象的引用
    public final boolean compareAndSet(long expect,long update){
        return unsafe.compareAndSwapLong(this,valueOffset,expect,update);
    }

    //9.CAS自旋操作
    //本质是乐观锁，是使用CPU资源来减少锁阻塞带来的开销
    //无限循环，多个线程调用getAndAddLong方法更新同一个变量时，compareAndSwapLong是CAS操作，
    //只有一个线程可以更新成功，剩余失败的线程会循环一次获取变量的最新值，再次尝试CAS操作，直到CAS操作成功
    //问题：浪费CPU资源
    public final long getAndAddLong(Object paramObject,long paramLong1,long paramLong2){
        long l;

        do {
            l = unsafe.getLongVolatile(paramObject,paramLong1);
        } while (!unsafe.compareAndSwapLong(paramObject,paramLong1,1,1+paramLong2));

        return l;
    }

}
