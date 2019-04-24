package com.imooc.multi_thread.Class016;

import javafx.concurrent.Task;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 不要new线程，要用线程池
 * 1.线程的创建和销毁是有系统开销的，会把系统资源耗尽
 * 2.线程池好处：
 * （1）执行大量异步任务时线程池能够复用线程，提供较好地性能；
 * （2）线程池提供一种资源限制和管理的手段，比如限制线程的个数、动态新增线程等，每个
 *  ThreadPoolExecutor也保留了一些基本的统计数据，比如：
 *  getActiveCount当前激活的线程个数（正在执行任务的线程）
 *  getCompleteTaskCout已经完成的任务个数
 *  getTaskCount所有的任务个数，包括已经完成的、正在执行的、在队列里缓存的
 */
public class newThreadByThreadPool {

    static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(8,8,1, TimeUnit.MINUTES,
            new LinkedBlockingDeque<Runnable>(),new NamedThreadFactory("TASK-POOL"));

    private static void process(final Task task) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
//                    task.doTask();
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
//            Task task = new Task(i);
//            process(task);
        }
    }
}
