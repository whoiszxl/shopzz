package com.whoiszxl.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.whoiszxl.DistributedLock;
import com.whoiszxl.DistributedLockFactory;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.cache.SeckillItemCache;
import com.whoiszxl.entity.SeckillItem;
import com.whoiszxl.service.SeckillItemCachedService;
import com.whoiszxl.service.SeckillItemService;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SeckillItemCachedServiceImpl implements SeckillItemCachedService {

    private final static Cache<Long, SeckillItemCache> seckillItemGuavaCache = CacheBuilder
            .newBuilder()
            .initialCapacity(10) //容器初始化容量
            .concurrencyLevel(8) //并发写缓存的线程数
            .expireAfterWrite(10, TimeUnit.SECONDS) //写缓存后10秒过期
            .build();

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SeckillItemService seckillItemService;

    @Autowired
    private DistributedLockFactory distributedLockFactory;

    private final Lock localCacleUpdatelock = new ReentrantLock();


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
        String itemKey = RedisKeyPrefixConstants.CACHE_SECKILL_ITEM + seckillItemId;
        String itemJson = redisUtils.get(itemKey);
        SeckillItemCache seckillItemCache = JsonUtil.fromJson(itemJson, SeckillItemCache.class);

        if(seckillItemCache == null) {
            //缓存不存在，需要从数据库中读取，并更新缓存
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
        DistributedLock lock = distributedLockFactory.getDistributedLock(RedisKeyPrefixConstants.LOCK_GET_ITEM_FROM_DB + seckillItemId);

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

            redisUtils.setEx(RedisKeyPrefixConstants.CACHE_SECKILL_ITEM + seckillItemId, JsonUtil.toJson(seckillItemCache), 3, TimeUnit.SECONDS);
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
