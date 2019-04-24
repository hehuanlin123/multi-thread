package com.imooc.multi_thread.Class006;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 1.Random的缺陷：多个线程会使用同一个原子性种子变量，会导致对原子变量更新的竞争
 * 2.为解决多线程高并发下Random的缺陷，JUC包下新增了ThreadLocalRandom类
 * 3.每个线程维护一个自己的种子变量，每个线程生成随机数时根据自己老的种子计算新的种
 * 子，并使用新的种子更新老的种子，然后根据新的种子计算随机数，不会存在竞争问题，提
 * 高并发性能
 */
public class NewRandomTest {

    public static void main(String[] args) {
        //获取一个随机数生成器
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        //输出10个在0~5（包含0，不包含5）之间的随机数
        //也是会有重复的
        for (int i = 0; i < 10; i++) {
            System.out.println(threadLocalRandom.nextInt(5));
        }

    }
}
