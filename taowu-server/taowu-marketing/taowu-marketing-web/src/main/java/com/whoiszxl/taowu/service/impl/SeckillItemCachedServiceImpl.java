package com.whoiszxl.taowu.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.whoiszxl.taowu.cqrs.cache.SeckillItemCache;
import com.whoiszxl.taowu.entity.SeckillItem;
import com.whoiszxl.taowu.service.SeckillItemCachedService;
import com.whoiszxl.taowu.service.SeckillItemService;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.starter.lock.DistributedLock;
import com.whoiszxl.taowu.starter.lock.DistributedLockFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 秒杀活动item缓存服务接口实现
 *
 * @author whoiszxl
 * @date 2022/4/20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillItemCachedServiceImpl implements SeckillItemCachedService {

    private final static Cache<Long, SeckillItemCache> seckillItemGuavaCache = CacheBuilder
            .newBuilder()
            .initialCapacity(10) //容器初始化容量
            .concurrencyLevel(8) //并发写缓存的线程数
            .expireAfterWrite(10, TimeUnit.SECONDS) //写缓存后10秒过期
            .build();

    private final RedisUtils redisUtils;

    private final SeckillItemService seckillItemService;

    private final DistributedLockFactory distributedLockFactory;

    private final Lock localCacleUpdatelock = new ReentrantLock();

    private final RedissonClient redissonClient;


    @Override
    public SeckillItemCache getCachedSeckillItem(Long seckillItemId, Long version) {
        SeckillItemCache seckillItemCache = SeckillItemCachedServiceImpl.seckillItemGuavaCache.getIfPresent(seckillItemId);

        //如果本地缓存存在
        if(seckillItemCache != null) {
            //不查版本号，直接命中本地缓存
            if(version == null) {
                return seckillItemCache;
            }

            //如果传入版本号等于或小于当前版本号，则也直接命中
            if(version.equals(seckillItemCache.getVersion()) || version < seckillItemCache.getVersion()) {
                return seckillItemCache;
            }

            //如果传入版本号大于当前版本号，则去查询分布式缓存
            if(version > seckillItemCache.getVersion()) {
                return getDistributedCache(seckillItemId);
            }

        }

        //本地缓存不存在，从分布式缓存中获取
        return getDistributedCache(seckillItemId);
    }

    /**
     * 从分布式缓存中获取item
     * @param seckillItemId
     * @return
     */
    private SeckillItemCache getDistributedCache(Long seckillItemId) {
        String itemKey = RedisPrefixConstants.Marketing.CACHE_SECKILL_ITEM + seckillItemId;
        String itemJson = redisUtils.get(itemKey);
        SeckillItemCache seckillItemCache = JsonUtil.fromJson(itemJson, SeckillItemCache.class);

        if(seckillItemCache == null) {
            //缓存不存在，需要从数据库中读取，并更新缓存
//            RBloomFilter<String> testBloom = redissonClient.getBloomFilter("test_bloom");
//
//            boolean contains = testBloom.contains(seckillItemId.toString());
//            if(!contains) {
//                return new SeckillItemCache().tryLater();
//            }
            //获取之前先从布隆过滤器里判断一下秒杀itemId是否存在，防止缓存穿透
            seckillItemCache = loadDb(seckillItemId);
        }

        if(seckillItemCache != null && !seckillItemCache.isLater()) {
            boolean isLockSuccess = localCacleUpdatelock.tryLock();
            if(isLockSuccess) {
                try{
                    seckillItemGuavaCache.put(seckillItemId, seckillItemCache);
                    log.info("更新了本地item缓存:{}", seckillItemCache);
                }finally {
                    localCacleUpdatelock.unlock();
                }
            }
        }

        return seckillItemCache;

    }

    private SeckillItemCache loadDb(Long seckillItemId) {
        DistributedLock lock = distributedLockFactory.getDistributedLock(RedisPrefixConstants.Marketing.LOCK_GET_ITEM_FROM_DB + seckillItemId);

        try{
            boolean isLockSuccess = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if(!isLockSuccess) {
                return new SeckillItemCache().tryLater();
            }

            SeckillItem seckillItem = seckillItemService.getById(seckillItemId);
            SeckillItemCache seckillItemCache;
            if(seckillItem == null) {
                seckillItemCache = new SeckillItemCache().notExist();
            }else {
                seckillItemCache = new SeckillItemCache().with(seckillItem);
            }

            redisUtils.setEx(RedisPrefixConstants.Marketing.CACHE_SECKILL_ITEM + seckillItemId, JsonUtil.toJson(seckillItemCache), 3, TimeUnit.SECONDS);
            log.info("从数据库加载数据，并保存到redis中:{}", seckillItemCache);
            return seckillItemCache;
        }catch (Exception e) {
            log.error("从数据库加载数据失败", e);
            return new SeckillItemCache().tryLater();
        }finally {
            lock.unlock();
        }

    }
}
