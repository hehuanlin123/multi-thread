package com.imooc.multi_thread.Class016;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义命名线程工厂
 */
public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolNumber = new AtomicInteger(1);

    private final ThreadGroup group;

    private static final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String namePrefix;

    NamedThreadFactory(String name){
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        if(null == name || name.isEmpty()){
            name = "pool";
        }
        namePrefix = name + "-" + poolNumber.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group,r,namePrefix + threadNumber.getAndIncrement(),0);
        if(t.isDaemon()){
            t.setDaemon(false);
        }
        if(t.getPriority() != Thread.NORM_PRIORITY){
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
