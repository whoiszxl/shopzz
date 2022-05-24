package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.whoiszxl.DistributedLock;
import com.whoiszxl.DistributedLockFactory;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.cache.SeckillCache;
import com.whoiszxl.cqrs.cache.SeckillItemListCache;
import com.whoiszxl.cqrs.cache.SeckillListCache;
import com.whoiszxl.entity.Seckill;
import com.whoiszxl.entity.SeckillItem;
import com.whoiszxl.service.SeckillCachedService;
import com.whoiszxl.service.SeckillItemService;
import com.whoiszxl.service.SeckillService;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    private static final Cache<Long, SeckillCache> seckillGuavaCache = CacheBuilder
            .newBuilder()
            .initialCapacity(10) //容器初始化容量
            .concurrencyLevel(8) //并发写缓存的线程数
            .expireAfterWrite(10, TimeUnit.SECONDS) //写缓存后10秒过期
            .build();

    private static final Cache<String, SeckillListCache> seckillListGuavaCache = CacheBuilder.newBuilder().initialCapacity(10).concurrencyLevel(5).expireAfterWrite(10, TimeUnit.SECONDS).build();
    private static final String SECKILL_LIST_KEY = "SECKILL_LIST_KEY";

    private static final Cache<Long, SeckillItemListCache> seckillItemListGuavaCache = CacheBuilder.newBuilder().initialCapacity(10).concurrencyLevel(5).expireAfterWrite(10, TimeUnit.SECONDS).build();
    private static final String SECKILL_ITEM_LIST_KEY = "SECKILL_ITEM_LIST_KEY";

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private SeckillItemService seckillItemService;

    @Autowired
    private DistributedLockFactory distributedLockFactory;

    private final Lock localCacheUpdatelock = new ReentrantLock();
    private final Lock localSeckillListCacheUpdatelock = new ReentrantLock();
    private final Lock localSeckillItemListCacheUpdatelock = new ReentrantLock();

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
            boolean isLockSuccess = localCacheUpdatelock.tryLock();
            if(isLockSuccess) {
                try{
                    seckillGuavaCache.put(seckillId, seckillCache);
                    log.info("更新了本地秒杀活动缓存:{}", seckillCache);
                }finally {
                    localCacheUpdatelock.unlock();
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

    @Override
    public SeckillListCache getCachedSeckillList(Long version) {
        SeckillListCache seckillListCache = seckillListGuavaCache.getIfPresent(SECKILL_LIST_KEY);
        if(seckillListCache != null) {
            if(version == null) {
                log.info("getSeckillListCache|获取秒杀活动列表命中本地缓存");
                return seckillListCache;
            }

            if(version <= seckillListCache.getVersion()) {
                log.info("getSeckillListCache|获取秒杀活动列表命中本地缓存");
                return seckillListCache;
            }
        }
        return getSeckillListByDistributedCache();
    }

    private SeckillListCache getSeckillListByDistributedCache() {
        log.info("getSeckillListCache|从分布式缓存获取秒杀列表");
        String seckillListKey = RedisKeyPrefixConstants.CACHE_SECKILL_LIST;
        String seckillListJson = redisUtils.get(seckillListKey);
        SeckillListCache seckillListCache = JsonUtil.fromJson(seckillListJson, SeckillListCache.class);

        if(seckillListCache == null) {
            //缓存不存在，需要从数据库中读取，并更新缓存
            seckillListCache = loadSeckillListFromDb();
        }

        if(!seckillListCache.isLater()) {
            boolean isLockSuccess = localSeckillListCacheUpdatelock.tryLock();
            if(isLockSuccess) {
                try{
                    seckillListGuavaCache.put(SECKILL_LIST_KEY, seckillListCache);
                    log.info("getSeckillListCache|更新了本地秒杀活动列表缓存|{}", seckillListCache);
                }finally {
                    localSeckillListCacheUpdatelock.unlock();
                }
            }
        }

        return seckillListCache;

    }

    private SeckillListCache loadSeckillListFromDb() {
        log.info("getSeckillListCache|从DB获取秒杀活动列表");
        DistributedLock lock = distributedLockFactory.getDistributedLock(RedisKeyPrefixConstants.LOCK_GET_SECKILL_LIST_FROM_DB);

        try{
            boolean lockFlag = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if(!lockFlag) {
                return new SeckillListCache().tryLater();
            }

            List<Seckill> seckillList = seckillService.list();
            SeckillListCache seckillListCache;
            if(seckillList.isEmpty()) {
                seckillListCache = new SeckillListCache().notExist();
            }else {
                seckillListCache = new SeckillListCache()
                        .setSeckillList(seckillList)
                        .setTotal(seckillList.size())
                        .setVersion(System.currentTimeMillis());
            }

            redisUtils.setEx(RedisKeyPrefixConstants.CACHE_SECKILL_LIST, JsonUtil.toJson(seckillListCache), 5, TimeUnit.MINUTES);
            log.info("getSeckillListCache|从DB获取秒杀活动列表，更新远程缓存成功");
            return seckillListCache;
        }catch (Exception e) {
            log.error("getSeckillListCache|从DB获取秒杀活动列表，更新远程缓存失败", e);
            return new SeckillListCache().tryLater();
        }finally {
            lock.unlock();
        }

    }


    @Override
    public SeckillItemListCache getCachedSeckillItemList(Long seckillId, Long version) {
        SeckillItemListCache seckillItemListCache = seckillItemListGuavaCache.getIfPresent(seckillId);
        if(seckillItemListCache != null) {
            if(version == null) {
                log.info("getSeckillItemListCache|获取秒杀商品列表命中本地缓存|{}", seckillId);
                return seckillItemListCache;
            }

            if(version <= seckillItemListCache.getVersion()) {
                log.info("getSeckillItemListCache|获取秒杀商品列表命中本地缓存|{},{}", seckillId, version);
                return seckillItemListCache;
            }
        }

        return getSeckillItemListByDistributedCache(seckillId);
    }

    private SeckillItemListCache getSeckillItemListByDistributedCache(Long seckillId) {
        log.info("getSeckillItemListCache|从分布式缓存中获取秒杀商品列表|{}", seckillId);
        String seckillItemListKey = RedisKeyPrefixConstants.CACHE_SECKILL_ITEM_LIST;
        String seckillItemListJson = redisUtils.get(seckillItemListKey);
        SeckillItemListCache seckillItemListCache = JsonUtil.fromJson(seckillItemListJson, SeckillItemListCache.class);

        if(seckillItemListCache == null) {
            //缓存不存在，需要从数据库中读取，并更新缓存
            seckillItemListCache = loadSeckillItemListFromDb(seckillId);
        }

        if(!seckillItemListCache.isLater()) {
            boolean isLockSuccess = localSeckillItemListCacheUpdatelock.tryLock();
            if(isLockSuccess) {
                try{
                    seckillItemListGuavaCache.put(seckillId, seckillItemListCache);
                    log.info("getSeckillItemListCache|更新了本地秒杀商品缓存|{}", seckillId);
                }finally {
                    localSeckillItemListCacheUpdatelock.unlock();
                }
            }
        }

        return seckillItemListCache;

    }

    private SeckillItemListCache loadSeckillItemListFromDb(Long seckillId) {
        log.info("getSeckillItemListCache|从数据库获取秒杀商品列表|{}", seckillId);
        DistributedLock lock = distributedLockFactory.getDistributedLock(RedisKeyPrefixConstants.LOCK_GET_SECKILL_ITEM_LIST_FROM_DB + seckillId);
        try{
            boolean lockFlag = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if(!lockFlag) {
                return new SeckillItemListCache().tryLater();
            }

            List<SeckillItem> seckillItemList = seckillItemService.list(Wrappers.<SeckillItem>lambdaQuery().eq(SeckillItem::getSeckillId, seckillId));
            SeckillItemListCache seckillItemListCache;
            if(seckillItemList.isEmpty()) {
                seckillItemListCache = new SeckillItemListCache().notExist();
            }else {
                seckillItemListCache = new SeckillItemListCache()
                        .setTotal(seckillItemList.size())
                        .setSeckillItemList(seckillItemList)
                        .setVersion(System.currentTimeMillis());
            }

            redisUtils.setEx(RedisKeyPrefixConstants.CACHE_SECKILL_ITEM_LIST, JsonUtil.toJson(seckillItemListCache), 5, TimeUnit.MINUTES);
            log.info("getSeckillItemListCache|从数据库获取秒杀商品列表,更新分布式缓存成功|{}", seckillId);
            return seckillItemListCache;
        }catch (Exception e) {
            log.error("getSeckillItemListCache|从数据库获取秒杀商品列表,更新分布式缓存失败|{}", seckillId, e);
            return new SeckillItemListCache().tryLater();
        }finally {
            lock.unlock();
        }

    }
}
