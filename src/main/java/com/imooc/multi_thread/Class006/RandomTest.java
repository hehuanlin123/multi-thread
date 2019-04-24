package com.imooc.multi_thread.Class006;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 1.在多线程下，多个线程可能都是拿同一个老种子去计算新的种子，导致多个线程产生的新种子是一样的
 * 2.生成随机值的算法是固定的，会导致多个线程产生相同的随机数
 */
public class RandomTest {

    private AtomicLong seed;

    public static void main(String[] args){

        //（1）创建一个默认种子的随机数生成器
        Random random = new Random();
        //（2）输出10个0~5(包含0，不包含5)之间的随机数
        //肯定会有重复的
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }

    /**
     * nextInt生成随机数:
     * (1)根据老的种子生成新的种子
     * (2)根据新的种子计算新的随机数
     * @param bound
     * @return
     */
    public int nextInt(int bound){
        //（3）参数检查
        if (bound <= 0){
            throw new IllegalArgumentException("BadBound");
        }
        //（4）根据老的种子生成新的种子
        // 抽象为seed=f(seed),其中f是一个固定函数，如seed=f(seed)=a*seed + b
        int r = next(31);
        //（5）根据新的种子生成随机数
        // 抽象为g(seed,bound)，其中g是一个固定函数，如g(seed,bound)=(int)((bound * (long)seed) >> 31)
        return r;
    }

    /**
     * 随机生成种子函数next
     * Random函数使用一个原子变量达到了这个效果，在创建Random对象时初始化的种子就保存到了种子原子变量里面
     * @param bits
     * @return
     */
    protected int next(int bits){

        long oldseed,nextseed;
        int multiplier = 2;
        int addend = 1;
        int mask = 1;

        AtomicLong seed = this.seed;

        do {
            //获取当前原子变量种子的值
            oldseed = seed.get();
            //根据当前种子值计算新的种子
            nextseed = (oldseed * multiplier + addend) & mask;
        //使用CAS操作
        } while(!seed.compareAndSet(oldseed,nextseed));
        //使用固定算法根据新的种子计算随机数
        return (int)(nextseed >>> (48 - bits));
    }
}
