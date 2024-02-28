package com.whoiszxl.taowu.starter.lock;

/**
 * 分布式锁工厂
 *
 * @author whoiszxl
 */
public interface DistributedLockFactory {

    /**
     * 获取分布式锁
     * @param key 锁key
     * @return
     */
    DistributedLock getDistributedLock(String key);

}
