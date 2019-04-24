/*
package com.imooc.multi_thread.Class013;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import com.sun.org.apache.bcel.internal.Constants;

import java.net.URL;
import java.util.concurrent.*;

public abstract class FixedThreadPool implements ThreadPool {

    public Executor getExecutor(URL url){
        String name = url.getParameter(Constants.THREAD_NAME_KEY,Constants.DEFAULT_THREAD_NAME);
        int threads = url.getParameter(Constants.THREADS_KEY,Constants.DEFAULT_THREADS);
        int queues = url.getParamenter(Constants.QUEUES_KEY,Constants.DEFAULT_QUEUES);
        //使用ThreadPoolExecutor创建了: 核心线程数=最大线程池数=threads的线程池
        return new ThreadPoolExecutor(threads,threads,0, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>() :
                        (queues < 0 ? new LinkedBlockingDeque<Runnable>() :
                                new LinkedBlockingDeque<Runnable>(queues)
                        ),
                        new NamedThreadFactory(name,true),
                        new AbortPolicyWithReport(name,url)
                );
    }
}
*/
