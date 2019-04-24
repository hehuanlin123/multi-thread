package com.imooc.multi_thread.Class004;

import sun.misc.Unsafe;

//LongAdder内部维护多个Cell变量，解决高并发下AtomicLong的缺点
//在同等并发量下，争夺单个变量更新操作的线程量会减少，变相减少了争夺共享资源的并发量

//使用@sun.misc.Contended是为了避免【伪共享】的问题

/*缓存系统中是以缓存行（cache line）为单位存储的。缓存行是2的整数幂个连续字节，
一般为32-256个字节。最常见的缓存行大小是64个字节。当多线程修改互相独立的变量时，
如果这些变量共享同一个缓存行，就会无意中影响彼此的性能，这就是伪共享。*/

@sun.misc.Contended final class Cell {

    volatile long value;

    public Cell(long x) {
        value = x;
    }

    final boolean cas(long cmp,long val){
        return UNSAFE.compareAndSwapLong(this,valueOffSet,cmp,val);
    }

    //Unsafe机制
    private static final sun.misc.Unsafe UNSAFE;
    private static final long valueOffSet;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();//反射获取Unsafe实例
            Class<?> ak = Cell.class;
            valueOffSet = UNSAFE.objectFieldOffset(ak.getDeclaredField("value"));
        } catch(Exception ex) {
            throw new Error(ex);
        }
    }

    /*分而治之的策略，先把并发量分担到多个原子变量上，让多个线程并发地不同的原子变量进行操作，
    然后获取计数时再把所有原子变量的计数和累加*/
    public long sum(){
        //LongAdder维护了一个延迟初始化的原子性更新数组（默认情况下Cell数组的值为null）
        //Cells占用的内存是比较大的，所以一开始并不创建，而是在需要时再创建，也就是惰性加载
        Cell[] cells = null;
        Cell[] as = cells;
        Cell a;
        /*LongAdder维护了一个基值变量；一开始Cell数组是null，并且并发线程数较少时，所有的累加操作
        都是对base变量进行的，这时就退化为AtomicLong；Cell数组的大小保持是2的N次方大小，初始化
        的时候Cell数组中Cell的元素个数为2，数组里面的实体变量是Cell类型*/
        long base = 0;
        long sum = base;
        if(as != null){
            for (int i = 0; i < as.length; i++) {
                if((a = as[i]) != null){
                    sum += a.value;
                }
            }
        }
        return sum;
    }
}
