package com.imooc.multi_thread.Class007;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写时拷贝策略：修改操作和元素迭代操作都是在底层创建一个拷贝的数组（快照）上进行
 * @param <E>
 */
public abstract class CopyOnWriteArrayList<E>
        implements List<E>, RandomAccess,Cloneable,java.io.Serializable {

    /*独占锁*/  /*transient不参与序列化过程*/
    //ReentrantLock独占锁保证同时只有一个线程对array修改
    final transient ReentrantLock lock = new ReentrantLock();

    /*array数组*/
    private transient volatile Object[] array;

    /**
     * 无参构造函数
     */
    public CopyOnWriteArrayList() {
        setArray(new Object[0]);
    }

    /**
     * 创建一个list，其内部元素是入参toCopyIn的拷贝
     * @param toCopyIn
     */
    public CopyOnWriteArrayList(E[] toCopyIn) {
        setArray(Arrays.copyOf(toCopyIn,toCopyIn.length,Object[].class));
    }

    /**
     * 入参为集合，拷贝集合里面元素到本list
     * @param collection
     */
    public CopyOnWriteArrayList(Collection<? extends E> collection) {
        Object[] elements;
        if (collection.getClass() == CopyOnWriteArrayList.class) {
            elements = ((CopyOnWriteArrayList<?>)collection).getArray();
        } else {
            elements = collection.toArray();
            //collection.toArray()可能不会正确返回Object[]
            if (elements.getClass() != Object[].class){
                elements = Arrays.copyOf(elements,elements.length,Object[].class);
            }
        }
        setArray(elements);
    }

    public boolean add(E e){

        //（1）加独占锁
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            //（2）获取array
            Object[] elements = getArray();

            //（3）拷贝array到新数组，添加元素到新数组
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements,len+1);
            newElements[len] = e;

            //（4）使用新数组替换添加前的数组
            setArray(newElements);
            return true;

        } finally {
            //（5）释放独占锁
            lock.unlock();
        }
    }

    private Object[] getArray() {
        return array;
    }

    private void setArray(Object[] objects) {
    }

    public E get(int index){
        return get(getArray(),index);
    }

    public E get(Object[] a,int index){
        return (E) a[index];
    }

    public E set(int index,E element){
        final ReentrantLock lock = this.lock;
        lock.lock();

        try {
            Object[] elements = getArray();
            E oldValue = get(elements,index);

            if (oldValue != element){
                int len = elements.length;
                Object[] newElements = Arrays.copyOf(elements,len);
                newElements[index] = element;
                setArray(newElements);
            } else {
                setArray(elements);
            }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }

    public E remove(int index){
        //（1）获取独占锁
        final ReentrantLock lock = this.lock;
        lock.lock();

        try {
            //（2）获取数组
            Object[] elements = getArray();
            int len = elements.length;

            //（3）获取指定元素
            E oldValue = get(elements,index);
            int numMoved = len - index -1;

            //（4）如果要删除的是最后一个元素
            if (numMoved == 0){
                setArray(Arrays.copyOf(elements,len - 1));
            } else {
                //（5）分两次拷贝删除后剩余的元素到新数组
                Object[] newElements = new Object[len - 1];
                System.arraycopy(elements,0,newElements,0,index);
                System.arraycopy(elements,index + 1,newElements,index,numMoved);

                //（6）使用新数组代替老的
                setArray(newElements);
            }
            return oldValue;
        } finally {
            //（7）释放锁
            lock.unlock();
        }
    }

}
