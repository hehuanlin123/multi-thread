# 1.CAS概述

- CAS即Compare And Swap，是JDK提供的非阻塞原子性操作，通过硬件保证了比较-更新操作的原子性
- Unsafe类提供的compareAndSwap*方法
- compareAndSwapLong
```
/**
 * 如果对象obj中内存偏移量为valueOffset位置的变量值为expect则使用新值update替换旧值expect
 * @param obj  对象内存位置
 * @param valueOffset  对象中变量的偏移量
 * @param expect  变量预期值
 * @param update  新的值
 * @return
 */
boolean compareAndSwapLong(Object obj,long valueOffset,long expect,long update);
```
# 2.AtomicLong的实现原理
- AtomicLong是使用无锁CAS算法实现的线程安全的计数器类，其保证了多线程下更新内部的long性变量值
的原子性和访问变量的内存可见性

- AtomicLong本质是使用Unsafe类提供了一系列CAS方法来实现线程安全的原子变量