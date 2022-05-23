package com.whoiszxl.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.whoiszxl.DistributedLock;
import com.whoiszxl.DistributedLockFactory;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.cache.SeckillCache;
import com.whoiszxl.entity.Seckill;
import com.whoiszxl.service.SeckillCachedService;
import com.whoiszxl.service.SeckillService;
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
public class SeckillCachedServiceImpl implements SeckillCachedService {

    private final static Cache<Long, SeckillCache> seckillGuavaCache = CacheBuilder
            .newBuilder()
            .initialCapacity(10) //容器初始化容量
            .concurrencyLevel(8) //并发写缓存的线程数
            .expireAfterWrite(10, TimeUnit.SECONDS) //写缓存后10秒过期
            .build();

    private final static Cache<String, SeckillCache> flashActivitiesLocalCache = CacheBuilder.newBuilder().initialCapacity(10).concurrencyLevel(5).expireAfterWrite(10, TimeUnit.SECONDS).build();


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private DistributedLockFactory distributedLockFactory;

    private final Lock localCacleUpdatelock = new ReentrantLock();

    @Override
    public SeckillCache getCachedSeckill(Long seckillId, Long version) {
        SeckillCache seckillCache = SeckillCachedServiceImpl.seckillGuavaCache.getIfPresent(seckillId);

        //如果本地缓存存在
        if(seckillCache != null) {
            //不查版本号，直接命中本地缓存
            if(version == null) {
                return seckillCache;
            }

            //如果传入版本号等于或小于当前版本号，则也直接命中
            if(version.equals(seckillCache.getVersion()) || version < seckillCache.getVersion()) {
                return seckillCache;
            }

            //如果传入版本号大于当前版本号，则去查询分布式缓存
            if(version > seckillCache.getVersion()) {
                return getDistributedCache(seckillId);
            }

        }

        //本地缓存不存在，从分布式缓存中获取
        return getDistributedCache(seckillId);
    }

    /**
     * 从分布式缓存中获取秒杀活动
     * @param seckillId 秒杀活动ID
     * @return
     */
    private SeckillCache getDistributedCache(Long seckillId) {
        String seckillKey = RedisKeyPrefixConstants.CACHE_SECKILL + seckillId;
        String seckillJson = redisUtils.get(seckillKey);
        SeckillCache seckillCache = JsonUtil.fromJson(seckillJson, SeckillCache.class);

        if(seckillCache == null) {
            //缓存不存在，需要从数据库中读取，并更新缓存
            seckillCache = loadDb(seckillId);
        }

        if(seckillCache != null && !seckillCache.isLater()) {
            boolean isLockSuccess = localCacleUpdatelock.tryLock();
            if(isLockSuccess) {
                try{
                    seckillGuavaCache.put(seckillId, seckillCache);
                    log.info("更新了本地秒杀活动缓存:{}", seckillCache);
                }finally {
                    localCacleUpdatelock.unlock();
                }
            }
        }

        return seckillCache;

    }

    private SeckillCache loadDb(Long seckillId) {
        DistributedLock lock = distributedLockFactory.getDistributedLock(RedisKeyPrefixConstants.LOCK_GET_SECKILL_FROM_DB + seckillId);

        try{
            boolean isLockSuccess = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if(!isLockSuccess) {
                return new SeckillCache().tryLater();
            }

            Seckill seckill = seckillService.getById(seckillId);
            SeckillCache seckillCache;
            if(seckill == null) {
                seckillCache = new SeckillCache().notExist();
            }else {
                seckillCache = new SeckillCache().with(seckill);
            }

            redisUtils.setEx(RedisKeyPrefixConstants.CACHE_SECKILL + seckillId, JsonUtil.toJson(seckillCache), 3, TimeUnit.MINUTES);
            log.info("从数据库加载数据，并保存到redis中:{}", seckillCache);
            return seckillCache;
        }catch (Exception e) {
            log.error("从数据库加载数据失败", e);
            return new SeckillCache().tryLater();
        }finally {
            lock.unlock();
        }

    }
}
