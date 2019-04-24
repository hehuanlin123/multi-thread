package com.imooc.multi_thread.Class019;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1.高并发时，同步调用应该考虑锁的性能。能用无锁，就不要用锁，一定要用锁的时候，加锁粒度尽量小。
 * 2.为避免多线程访问共享资源时的并发问题，一般需要在访问共享资源前进行适当同步，比如在处理共享
 * 资源前加独占锁，比如在处理共享资源前加独占锁，再处理资源，然后释放锁。
 */
public class SynchronizedTest {

    /**
     * 1.进入synchronized块前会清空锁块内本地内存中将会使用到的共享变量，然后调用getVal方法从主内存
     * 中获取变量的值，退出synchronized块会把修改刷新到主内存，从而保证了共享变量的可见性。
     * 2.synchronized块加的是独占锁，这导致线程只能有一个线程获取锁调用getVal方法获取当前变量的值，
     * 而getVal方法本身不会修改val的值，大大降低了读取的并发性。
     */
    public class ShareValueBySynchronized {

        private int val;

        public synchronized int getVal() {
            return val;
        }

        public synchronized void setVal(int val) {
            this.val = val;
        }
    }

    /**
     * 1.变量val不依赖原来的值，使用无锁的volatile可以解决内存不可见问题
     * 2.volatile只能保证内存可见性，不能保证原子性，例如计数器不能使用volatile
     */
    public class ShareValueByVolatile {

        private volatile int val;

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }

    /**
     * synchronized是独占锁，导致同时调用getVal方法读取val的值的线程竞争独占锁，
     * 降低了并发度，可以适当降低锁的范围，实现读写分离
     */
    public class SafeCount1 {

        private int val;

        public synchronized int getVal() {
            return val;
        }

        public synchronized void inc() {
            ++val;
        }
    }

    /**
     * 1.使用读写分离，多个线程可以同时获取读取锁然后获取val，增加了读取的并发度
     * 2.关于计数器，JUC（Java Util Concurrence）包提供了AtomicInteger、AtomicLong等类
     * 3.如果担心在高并发下，大量线程在进行CAS失败后进行自旋重试，LongAdder是更好的选择
     */
    public class SafeCount2 {

        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        private int val;

        public int getVal() {
            readLock.lock();
            try {
                return val;
            } catch (Exception e) {
                readLock.unlock();
            }
            return val;
        }

        public void inc() {
            writeLock.lock();
            try {
                ++val;
            } catch (Exception e) {
                writeLock.unlock();
            }
        }
    }
}
