package com.whoiszxl.taowu.starter.lock.redisson;

import com.whoiszxl.taowu.starter.lock.DistributedLock;
import com.whoiszxl.taowu.starter.lock.DistributedLockFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁工厂
 *
 * @author whoiszxl
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "taowu.redisson.enabled", havingValue = "true", matchIfMissing = true)
public class RedissonDistributedLockFactory implements DistributedLockFactory {

    private final RedissonClient redissonClient;

    @Override
    public DistributedLock getDistributedLock(String key) {
        RLock lock = redissonClient.getLock(key);

        return new DistributedLock() {
            @Override
            public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
                //log.info("key:{} 尝试加锁是否成功: {}", key, isLockSuccess);
                return lock.tryLock(waitTime, leaseTime, unit);
            }

            @Override
            public void lock(long leaseTime, TimeUnit unit) {
                lock.lock(leaseTime, unit);
            }

            @Override
            public void unlock() {
                if(isLocked() && isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }

            @Override
            public boolean isLocked() {
                return lock.isLocked();
            }

            @Override
            public boolean isHeldByThread(long threadId) {
                return lock.isHeldByThread(threadId);
            }

            @Override
            public boolean isHeldByCurrentThread() {
                return lock.isHeldByCurrentThread();
            }
        };
    }
}
