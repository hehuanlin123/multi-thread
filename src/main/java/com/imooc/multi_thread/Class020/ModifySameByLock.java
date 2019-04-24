/*
package com.imooc.multi_thread.Class020;

*/
/**
 * 并发修改同一条记录时需要加锁以避免丢失更新；
 * 锁可以是应用层的，可以是缓存层的，也可以是数据库层的；
 * 数据库层的两种方式：悲观锁、乐观锁。
 *//*


*/
/**
 * 如果每次访问冲突的概率小于20%，建议使用乐观锁，这是因为乐观锁本身不需要对行记录加锁，
 * 并发性能较好，另外使用乐观锁时重试次数不小于3次。如果每次访问冲突概率比较大，那么使
 * 用乐观锁会导致重试次数较高，并且等达到重试次数后，也不一定更新OK，所以这时候使用悲观锁。
 *//*


*/
/**
 * 对于应用层，一般使用分布式锁来做同步，缓存上可以使用Redis的CAS操作来做同步。
 *//*

public class ModifySameByLock {

    */
/**
     * 悲观锁指对数据被外界修改保持保守态度，认为数据很容易被其他线程修改，所以在数据处理之前先对数据进行加锁，
     * 并在整个数据处理过程中，使数据处于锁定状态。悲观锁的实现，往往依靠数据库提供的锁机制，数据库中实现是对
     * 数据记录操作前给记录加排他锁。如果获取锁失败，则说明数据正在被其他线程修改，当前线程则等待或者抛出异常。
     * 如果获取锁成功，则对记录进行操作，事务提交成功后释放排他锁。
     *//*


    //updateEntryByNegative、query、update使用了事务切面的方法，事务传播属性设置为required
    //执行updateEntryByNegative时，如果上层调用方法没有开启事务，则当前会开启一个事务，然后执行（1）
    public int updateEntryByNegative(long id){
        //(1)使用悲观锁获取指定记录
        //for update(锁定改行，直到事务提交)
        EntryObject entry = query("select * from table where id = #{id} for update",id);
        //(2)对entry实体进行修改，其中仓库数量减去1

        //(3)update操作
        int count = update("update table set num = #{num} where id = #{id}",entry);
        return count;
    }

    */
/**
     * 乐观锁是相对于悲观锁来说的，它认为数据一般情况下不会造成冲突，所以在访问记录前不会加排它锁，
     * 而是在数据提交更新的时候，才会正式对数据的冲突与否进行检测。具体来说，是根据update返回的行
     * 数让用户决定如何去做。
     *//*

    public int updateEntryByPositive(long id){
        //(1)使用乐观锁获取指定记录
        EntryObject entry = query("select * from table where id = #{id} for update",id);
        //(2)对entry实体进行修改，其中仓库数量减去1

        //(3)update操作
        //set语句version = #{version}+1
        //where语句version = #{version} and id = #{id}
        //意思是数据库里面version = #{version} and id = #{id}的记录存在则更新version的值加1，这个是无锁CAS操作
        int count = update("update table set num = #{num}，version = #{version}+1 where id = #{id} and version = #{version}",entry);
        return count;
    }

    */
/**
     *
     *//*

    public boolean updateEntry(long id){

        boolean reslut = false;

        int retryNum = 5;

        //retryNum设置更新失败后的重试次数
        while(retryNum > 0){
            //(1)使用悲观锁获取指定记录
            EntryObject entry = query("select * from table where id = #{id} for update",id);
            //(2)对entry实体进行修改，其中仓库数量减去1

            //(3)update操作
            //set语句version = #{version}+1
            //where语句version = #{version} and id = #{id}
            //意思是数据库里面version = #{version} and id = #{id}的记录存在则更新version的值加1，这个是无锁CAS操作
            int count = update("update table set num = #{num}，version = #{version}+1 where id = #{id} and version = #{version}",entry);
            if(count == 1){
                reslut = true;
                break;
            }
            retryNum--;
        }
        return reslut;
    }
}
*/
