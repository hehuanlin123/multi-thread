package com.imooc.multi_thread.Class018;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 1.SimpleDateFormat是线程不安全的，主要是其中存在共享变量，并且没有在访问共享变量前进行适当的同步处理
 * 2.解决线程安全的方法：
 * （1）定义工具类，每次使用时返回一个SimpleDateFormat的实例，这样可以保证每个实例使用自己的Calendar实例，
 *  但是每次使用都需要new一个对象，并且使用后由于没有其他引用，就会需要被回收，开销会很大；
 * （2）多线程中可以使用synchronized进行同步
 */
public class TestSimpleDateFormat {

    /*static SimpleDateFormat getSimpleDateFormatInstance(){
        return new SimpleDateFormat();
    }*/

    //（1）创建单例实例
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    public static void main(String[] args) {
        //（2）创建多个线程并启动
        for (int i = 0; i < 10; ++i) {
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {//（3）使用单例日期实例解析文本
                        //缺点：使用同步意味着【多个线程】要竞争锁，在【高并发】的场景下会导致系统响应性能下降
                        synchronized (sdf) {
                            System.out.println(sdf.parse("2019-04-03 16:45:22"));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();//（4）启动线程
        }
        
    }
}
