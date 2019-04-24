package com.imooc.multi_thread.Class005;

import com.alibaba.ans.shaded.org.apache.log4j.helpers.ThreadLocalMap;

import java.util.ArrayList;
import java.util.List;

/*ThreadLocal就是一个工具壳，它通过set方法把value值放入调用线程的threadLocals里面存放起来
当调用线程调用它的get方法时再从当前线程的threadLocals变量里面拿出来用*/

/*每个线程内部都有一个名字为threadLocals的成员变量，该变量类型为HashMap，
其中key为我们定义的ThreadLocal变量的this引用，value为我们set的值，
如果当前线程不消失，则本地变量会一直存在，从而造成内存溢出，
所以要调用remove方法删除对应线程的threadLocals中的本地变量*/

public class ThreadLocal {

    public void set(List<String> value){
        //（1）获取当前线程
        Thread thread = Thread.currentThread();
        // (2)当前线程的key为thread,去查找对应的线程变量threadLocalMap，找到则设置value
        ThreadLocalMap threadLocalMap = getMap(thread);
        if(threadLocalMap != null){
            threadLocalMap.set(value);
        } else {//（3）第一次调用则创建当前线程对应的HashMap
            createMap(thread,value);
        }
    }

    /**
     * 获取当前线程的threadLocals变量
     * @param thread
     * @return
     */
    ThreadLocalMap getMap(Thread thread){
//        return thread.threadLocals;
        return null;
    }

    /**
     * 创建当前线程的threadLocals变量
     * @param thread
     * @param firstValue
     */
    void createMap(Thread thread,List<String> firstValue){
//        thread.threadLocals = new ThreadLocalMap(this,firstValue);
    }

    public List<String> get(){
        //（4）获取当前线程
        Thread thread = Thread.currentThread();
        //（5）获取当前线程的threadLocals变量
        ThreadLocalMap threadLocalMap = getMap(thread);
        //（6）如果threadLocals不为null，则返回对应本地变量
        /*if(threadLocalMap != null){
            ThreadLocalMap.Entry e = threadLocalMap.getEntry(this);
            if(e != null){
                @SuppressWarnings("unchecked")
                List<String> result = (List<String>)e.value;
                return result;
            }
        }*/
        //（7）threadLocals为空则初始化当前线程的threadLocals成员变量
        return setInitialValue();
    }

    /**
     * 如果当前线程的threadLocals变量不为空，则设置当前线程的本地变量为null，
     * 否则调用createMap创建当前线程的本地变量
     * @return
     */
    public List<String> setInitialValue(){
        //（8）初始化为null
        List<String> value = (List<String>) initialValue();
        Thread thread = Thread.currentThread();
        ThreadLocalMap threadLocalMap = getMap(thread);
        //（9）如果当前线程的threadLocals变量不为空
        if(threadLocalMap != null){
            threadLocalMap.set(value);
        }
        //（10）如果当前线程的threadLocals变量为空
        if(threadLocalMap == null){
            createMap(thread,value);
        }
        return value;
    }

    protected T initialValue(){
        return null;
    }

    /**
     * 如果当前线程的threadLocals变量不为空，则删除当前线程中指定ThreadLocal实例的本地变量
     */
    public void remove(){
        ThreadLocalMap threadLocalMap = getMap(Thread.currentThread());
        if(threadLocalMap != null){
            threadLocalMap.remove();
        }
    }

    private class T {
    }

    public static void main(String[] args) {
        final ThreadLocal test = new ThreadLocal();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> strs = new ArrayList<String>();
                strs.add("1");
                strs.add("2");
                strs.add("3");
                test.set(strs);
                test.get();
                System.out.println("strs1 = " + strs);
            }
        },"t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> strs = new ArrayList<String>();
                strs.add("a");
                strs.add("b");
                test.set(strs);
                test.get();
                System.out.println("strs2 = " + strs);
            }
        },"t2").start();
    }
}
