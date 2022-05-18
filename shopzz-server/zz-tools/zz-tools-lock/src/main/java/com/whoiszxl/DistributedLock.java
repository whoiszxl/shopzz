package com.whoiszxl;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 *
 * @author whoiszxl
 * @date 2022/4/20
 */
public interface DistributedLock {

    /**
     * 尝试加锁
     * @param waitTime 尝试等待时间
     * @param leaseTime 加锁后等待多久释放
     * @param unit 时间单位
     * @return
     * @throws InterruptedException
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    /**
     * 加锁
     * @param leaseTime 加锁后等待多久释放
     * @param unit 时间单位
     */
    void lock(long leaseTime, TimeUnit unit);

    /**
     * 如果是锁定状态并且是当前线程持有锁就进行 解锁
     */
    void unlock();

    /**
     * 判断是否加锁
     * @return
     */
    boolean isLocked();

    /**
     * 判断是否是指定线程持有
     * @param threadId 指定线程id
     * @return
     */
    boolean isHeldByThread(long threadId);

    /**
     * 判断是否是当前线程持有
     * @return
     */
    boolean isHeldByCurrentThread();
}
