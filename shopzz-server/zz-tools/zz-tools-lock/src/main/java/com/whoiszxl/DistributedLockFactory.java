package com.whoiszxl;

/**
 * 分布式锁工厂
 *
 * @author whoiszxl
 * @date 2022/4/20
 */
public interface DistributedLockFactory {

    /**
     * 获取分布式锁
     * @param key 锁key
     * @return
     */
    DistributedLock getDistributedLock(String key);

}
