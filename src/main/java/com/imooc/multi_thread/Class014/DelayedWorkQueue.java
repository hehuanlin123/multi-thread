/*
package com.imooc.multi_thread.Class014;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

*/
/**
 * 获取元素的线程要在队列头部等待，为了最大化减少不必要的等待时间，DelayedWorkQueue在获取元素的时候借鉴了Leader-Follower的思想。
 * 当一个线程调用队列的【take方法】变为Leader线程后，会调用条件变量【available.awaitNanos(delay)】等待delay时间，但是其他线程（Follower线程）
 * 则会调用【available.await()】进行无限等待。等Leader线程延迟时间过期后，Leader线程会退出take方法，并通过调用【available.signal()】方法
 * 唤醒一个Follower线程，被唤醒的Follower线程被选为新的Leader线程。
 *//*

public class DelayedWorkQueue extends AbstractQueue<Runnable>
        implements BlockingQueue<Runnable> {

    private static final int INITIAL_CAPACITY = 16;
    private RunnableScheduledFuture<?>[] queue =
            new RunnableScheduledFuture<?>[INITIAL_CAPACITY];
    private final ReentrantLock lock = new ReentrantLock();
    private int size = 0;

    private Thread leader = null;

    private final Condition available = lock.newCondition();

    private void setIndex(RunnableScheduledFuture<?> f, int idx) {
        if (f instanceof ScheduledThreadPoolExecutor.ScheduledFutureTask)
            ((ScheduledThreadPoolExecutor.ScheduledFutureTask)f).heapIndex = idx;
    }

    private void siftUp(int k, RunnableScheduledFuture<?> key) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            RunnableScheduledFuture<?> e = queue[parent];
            if (key.compareTo(e) >= 0)
                break;
            queue[k] = e;
            setIndex(e, k);
            k = parent;
        }
        queue[k] = key;
        setIndex(key, k);
    }

    private void siftDown(int k, RunnableScheduledFuture<?> key) {
        int half = size >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            RunnableScheduledFuture<?> c = queue[child];
            int right = child + 1;
            if (right < size && c.compareTo(queue[right]) > 0)
                c = queue[child = right];
            if (key.compareTo(c) <= 0)
                break;
            queue[k] = c;
            setIndex(c, k);
            k = child;
        }
        queue[k] = key;
        setIndex(key, k);
    }

    private void grow() {
        int oldCapacity = queue.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); // grow 50%
        if (newCapacity < 0) // overflow
            newCapacity = Integer.MAX_VALUE;
        queue = Arrays.copyOf(queue, newCapacity);
    }

    private int indexOf(Object x) {
        if (x != null) {
            if (x instanceof ScheduledThreadPoolExecutor.ScheduledFutureTask) {
                int i = ((ScheduledThreadPoolExecutor.ScheduledFutureTask) x).heapIndex;
                // Sanity check; x could conceivably be a
                // ScheduledFutureTask from some other pool.
                if (i >= 0 && i < size && queue[i] == x)
                    return i;
            } else {
                for (int i = 0; i < size; i++)
                    if (x.equals(queue[i]))
                        return i;
            }
        }
        return -1;
    }

    public boolean contains(Object x) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return indexOf(x) != -1;
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(Object x) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int i = indexOf(x);
            if (i < 0)
                return false;

            setIndex(queue[i], -1);
            int s = --size;
            RunnableScheduledFuture<?> replacement = queue[s];
            queue[s] = null;
            if (s != i) {
                siftDown(i, replacement);
                if (queue[i] == replacement)
                    siftUp(i, replacement);
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return size;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    public RunnableScheduledFuture<?> peek() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return queue[0];
        } finally {
            lock.unlock();
        }
    }

    public boolean offer(Runnable x) {
        if (x == null)
            throw new NullPointerException();
        RunnableScheduledFuture<?> e = (RunnableScheduledFuture<?>)x;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int i = size;
            if (i >= queue.length)
                grow();
            size = i + 1;
            if (i == 0) {
                queue[0] = e;
                setIndex(e, 0);
            } else {
                siftUp(i, e);
            }
            if (queue[0] == e) {
                leader = null;
                available.signal();
            }
        } finally {
            lock.unlock();
        }
        return true;
    }

    public void put(Runnable e) {
        offer(e);
    }

    public boolean add(Runnable e) {
        return offer(e);
    }

    public boolean offer(Runnable e, long timeout, TimeUnit unit) {
        return offer(e);
    }

    private RunnableScheduledFuture<?> finishPoll(RunnableScheduledFuture<?> f) {
        int s = --size;
        RunnableScheduledFuture<?> x = queue[s];
        queue[s] = null;
        if (s != 0)
            siftDown(0, x);
        setIndex(f, -1);
        return f;
    }

    public RunnableScheduledFuture<?> poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            RunnableScheduledFuture<?> first = queue[0];
            if (first == null || first.getDelay(NANOSECONDS) > 0)
                return null;
            else
                return finishPoll(first);
        } finally {
            lock.unlock();
        }
    }

    public RunnableScheduledFuture<?> take() throws InterruptedException {
        final ReentrantLock lock = this.lock;//获取独占锁lock
        lock.lockInterruptibly();
        try {
            for (;;) {
                //（1）获取但不移除队首元素
                RunnableScheduledFuture<?> first = queue[0];
                if (first == null)// 线程A第一次调用take()方法时队列为空，执行(1)后first==null
                    available.await();//（2）把当前线程放入available的条件队列阻塞等待
                else {//如果线程A第一次调用take()方法时队列不为空，会通过delay = first.getDelay(TimeUnit.NANOSECONDS)
                      //获取头部元素还有多少时间就过期，如果delay <= 0则说明已经过期，则直接返回过期元素
                    long delay = first.getDelay(NANOSECONDS);
                    if (delay <= 0)//（3）
                        return finishPoll(first);
                    first = null; // don't retain ref while waiting
                    if (leader != null)//（4）
                        available.await();
                    else {//如果leader == null说明线程A是第一个调用take()方法的，则选取线程A为Leader线程，然后执行代码(6)
                          //线程A就会被阻塞挂起delay时间，这时候如果有其他线程调用了take方法，则直接调用代码(4)永久挂起(直到
                          //收到signal信号)，这些线程其实就是成为了Follower线程了
                        Thread thisThread = Thread.currentThread();
                        leader = thisThread;//（5）
                        try {
                            //（6）Leader线程A等delay时间到了后，会从代码(6)返回，然后设置leader为null，循环一次后，执行代码（3）
                            //从队头拿出过期元素。
                            available.awaitNanos(delay);
                        } finally {
                            if (leader == thisThread)
                                leader = null;
                        }
                    }
                }
            }
        } finally {
            if (leader == null && queue[0] != null)
                //（7）激活一个因为调用代码（4）而被阻塞的线程，这时Follower线程中的一个就会被激活，成为Leader线程。
                available.signal();
            lock.unlock();//（8）
        }
    }

    public RunnableScheduledFuture<?> poll(long timeout, TimeUnit unit)
            throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (;;) {
                RunnableScheduledFuture<?> first = queue[0];
                if (first == null) {
                    if (nanos <= 0)
                        return null;
                    else
                        nanos = available.awaitNanos(nanos);
                } else {
                    long delay = first.getDelay(NANOSECONDS);
                    if (delay <= 0)
                        return finishPoll(first);
                    if (nanos <= 0)
                        return null;
                    first = null; // don't retain ref while waiting
                    if (nanos < delay || leader != null)
                        nanos = available.awaitNanos(nanos);
                    else {
                        Thread thisThread = Thread.currentThread();
                        leader = thisThread;
                        try {
                            long timeLeft = available.awaitNanos(delay);
                            nanos -= delay - timeLeft;
                        } finally {
                            if (leader == thisThread)
                                leader = null;
                        }
                    }
                }
            }
        } finally {
            if (leader == null && queue[0] != null)
                available.signal();
            lock.unlock();
        }
    }

    public void clear() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            for (int i = 0; i < size; i++) {
                RunnableScheduledFuture<?> t = queue[i];
                if (t != null) {
                    queue[i] = null;
                    setIndex(t, -1);
                }
            }
            size = 0;
        } finally {
            lock.unlock();
        }
    }

    private RunnableScheduledFuture<?> peekExpired() {
        // assert lock.isHeldByCurrentThread();
        RunnableScheduledFuture<?> first = queue[0];
        return (first == null || first.getDelay(NANOSECONDS) > 0) ?
                null : first;
    }

    public int drainTo(Collection<? super Runnable> c) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            RunnableScheduledFuture<?> first;
            int n = 0;
            while ((first = peekExpired()) != null) {
                c.add(first);   // In this order, in case add() throws.
                finishPoll(first);
                ++n;
            }
            return n;
        } finally {
            lock.unlock();
        }
    }

    public int drainTo(Collection<? super Runnable> c, int maxElements) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        if (maxElements <= 0)
            return 0;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            RunnableScheduledFuture<?> first;
            int n = 0;
            while (n < maxElements && (first = peekExpired()) != null) {
                c.add(first);   // In this order, in case add() throws.
                finishPoll(first);
                ++n;
            }
            return n;
        } finally {
            lock.unlock();
        }
    }

    public Object[] toArray() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return Arrays.copyOf(queue, size, Object[].class);
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (a.length < size)
                return (T[]) Arrays.copyOf(queue, size, a.getClass());
            System.arraycopy(queue, 0, a, 0, size);
            if (a.length > size)
                a[size] = null;
            return a;
        } finally {
            lock.unlock();
        }
    }

    public Iterator<Runnable> iterator() {
        return new ScheduledThreadPoolExecutor.DelayedWorkQueue.Itr(Arrays.copyOf(queue, size));
    }

    private class Itr implements Iterator<Runnable> {
        final RunnableScheduledFuture<?>[] array;
        int cursor = 0;     // index of ext element to return
        int lastRet = -1;   // index of last element, or -1 if no such

        Itr(RunnableScheduledFuture<?>[] array) {
            this.array = array;
        }

        public boolean hasNext() {
            return cursor < array.length;
        }

        public Runnable next() {
            if (cursor >= array.length)
                throw new NoSuchElementException();
            lastRet = cursor;
            return array[cursor++];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            ScheduledThreadPoolExecutor.DelayedWorkQueue.this.remove(array[lastRet]);
            lastRet = -1;
        }
    }
}
*/
