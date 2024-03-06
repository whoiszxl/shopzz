package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.common.utils.LogFormatUtil;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.cache.SeckillCache;
import com.whoiszxl.taowu.cqrs.cache.SeckillItemListCache;
import com.whoiszxl.taowu.cqrs.cache.SeckillListCache;
import com.whoiszxl.taowu.entity.Seckill;
import com.whoiszxl.taowu.entity.SeckillItem;
import com.whoiszxl.taowu.mapper.SeckillMapper;
import com.whoiszxl.taowu.service.SeckillCachedService;
import com.whoiszxl.taowu.service.SeckillItemService;
import com.whoiszxl.taowu.starter.lock.DistributedLock;
import com.whoiszxl.taowu.starter.lock.DistributedLockFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
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
@RequiredArgsConstructor
public class SeckillCachedServiceImpl implements SeckillCachedService {

    private static final Cache<Long, SeckillCache> SECKILL_GUAVA_CACHE = CacheBuilder
            .newBuilder()
            .initialCapacity(10) //容器初始化容量
            .concurrencyLevel(8) //并发写缓存的线程数
            .expireAfterWrite(10, TimeUnit.SECONDS) //写缓存后10秒过期
            .build();

    private static final Cache<String, SeckillListCache> seckillListGuavaCache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .concurrencyLevel(5)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

    private static final String SECKILL_LIST_KEY = "SECKILL_LIST_KEY";

    private final RedissonClient redissonClient;

    private static final Cache<Long, SeckillItemListCache> seckillItemListGuavaCache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .concurrencyLevel(5)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();

    private static final String SECKILL_ITEM_LIST_KEY = "SECKILL_ITEM_LIST_KEY";

    private final RedisUtils redisUtils;

    private final SeckillMapper seckillMapper;

    private final SeckillItemService seckillItemService;

    private final DistributedLockFactory distributedLockFactory;

    private final Lock localCacheUpdatelock = new ReentrantLock();
    private final Lock localSeckillListCacheUpdatelock = new ReentrantLock();
    private final Lock localSeckillItemListCacheUpdatelock = new ReentrantLock();

    @Override
    public SeckillCache getCachedSeckill(Long seckillId, Long version) {
        SeckillCache seckillCache = SeckillCachedServiceImpl.SECKILL_GUAVA_CACHE.getIfPresent(seckillId);

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
        String seckillKey = RedisPrefixConstants.Marketing.CACHE_SECKILL + seckillId;
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
                    SECKILL_GUAVA_CACHE.put(seckillId, seckillCache);
                    log.info("更新了本地秒杀活动缓存:{}", seckillCache);
                }finally {
                    localCacheUpdatelock.unlock();
                }
            }
        }

        return seckillCache;

    }

    private SeckillCache loadDb(Long seckillId) {
        DistributedLock lock = distributedLockFactory.getDistributedLock(RedisPrefixConstants.Marketing.LOCK_GET_SECKILL_FROM_DB + seckillId);

        try{
            boolean isLockSuccess = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if(!isLockSuccess) {
                return new SeckillCache().tryLater();
            }

            Seckill seckill = seckillMapper.selectById(seckillId);
            SeckillCache seckillCache;
            if(seckill == null) {
                seckillCache = new SeckillCache().notExist();
            }else {
                seckillCache = new SeckillCache().with(seckill);
            }

            redisUtils.setEx(RedisPrefixConstants.Marketing.CACHE_SECKILL + seckillId, JsonUtil.toJson(seckillCache), 3, TimeUnit.MINUTES);
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
                log.info(LogFormatUtil.format(this, "获取秒杀活动列表命中本地缓存", null));
                return seckillListCache;
            }

            if(version <= seckillListCache.getVersion()) {
                log.info(LogFormatUtil.format(this, "获取秒杀活动列表命中本地缓存", null));
                return seckillListCache;
            }
        }
        return getSeckillListByDistributedCache();
    }

    private SeckillListCache getSeckillListByDistributedCache() {
        log.info(LogFormatUtil.format(this, "从分布式缓存获取秒杀列表", null));
        String seckillListKey = RedisPrefixConstants.Marketing.CACHE_SECKILL_LIST;
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
                    log.info(LogFormatUtil.format(this, "更新了本地秒杀活动列表缓存", seckillListCache));
                }finally {
                    localSeckillListCacheUpdatelock.unlock();
                }
            }
        }

        return seckillListCache;

    }

    private SeckillListCache loadSeckillListFromDb() {
        log.info(LogFormatUtil.format(this, "从DB获取秒杀活动列表", null));
        DistributedLock lock = distributedLockFactory.getDistributedLock(RedisPrefixConstants.Marketing.LOCK_GET_SECKILL_LIST_FROM_DB);

        try{
            boolean lockFlag = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if(!lockFlag) {
                return new SeckillListCache().tryLater();
            }

            List<Seckill> seckillList = seckillMapper.selectList(null);
            SeckillListCache seckillListCache;
            if(seckillList.isEmpty()) {
                seckillListCache = new SeckillListCache().notExist();
            }else {
                seckillListCache = new SeckillListCache()
                        .setSeckillList(seckillList)
                        .setTotal(seckillList.size())
                        .setVersion(System.currentTimeMillis());
            }

            redisUtils.setEx(RedisPrefixConstants.Marketing.CACHE_SECKILL_LIST, JsonUtil.toJson(seckillListCache), 5, TimeUnit.MINUTES);
            log.info(LogFormatUtil.format(this, "从DB获取秒杀活动列表，更新远程缓存成功", null));
            return seckillListCache;
        }catch (Exception e) {
            log.error(LogFormatUtil.format(this, "从DB获取秒杀活动列表，更新远程缓存失败", null), e);
            return new SeckillListCache().tryLater();
        }finally {
            lock.unlock();
        }

    }


    @Override
    public SeckillItemListCache getCachedSeckillItemList(Long seckillId, Long version) {
        //1. 从本地缓存获取秒杀品列表数据
        SeckillItemListCache seckillItemListCache = seckillItemListGuavaCache.getIfPresent(seckillId);

        //2. 如果本地缓存不为空
        if(seckillItemListCache != null) {
            //2.1 版本号为空则走无版本号流程，直接将本地缓存返回，存在缓存不一致的问题
            if(version == null) {
                log.info(LogFormatUtil.format(this, "获取秒杀商品列表命中本地缓存", seckillId));
                return seckillItemListCache;
            }

            //2.2 版本号存在则判断传入版本号是否小于等于缓存内的版本号，如果小于等于则说明本地缓存为最新
            if(version <= seckillItemListCache.getVersion()) {
                log.info(LogFormatUtil.format(this, "获取秒杀商品列表命中本地缓存", seckillId, version));
                return seckillItemListCache;
            }
        }

        //3. 本地缓存不存在，则去分布式缓存获取
        return getSeckillItemListByDistributedCache(seckillId);
    }

    private SeckillItemListCache getSeckillItemListByDistributedCache(Long seckillId) {
        log.info(LogFormatUtil.format(this, "从分布式缓存中获取秒杀商品列表", seckillId));
        String seckillItemListKey = RedisPrefixConstants.Marketing.CACHE_SECKILL_ITEM_LIST + seckillId;

        //1. 从分布式缓存Redis中获取秒杀品列表数据
        String seckillItemListJson = redisUtils.get(seckillItemListKey);
        SeckillItemListCache seckillItemListCache = JsonUtil.fromJson(seckillItemListJson, SeckillItemListCache.class);


        //2. 分布式缓存中不存在列表，需要从数据库中读取，并更新缓存
        if(seckillItemListCache == null) {
            RBloomFilter<String> seckillBloom = redissonClient.getBloomFilter(RedisPrefixConstants.Marketing.BLOOM_SECKILL_ID);
            boolean contains = seckillBloom.contains(seckillId.toString());
            if(!contains) {
                seckillItemListCache = new SeckillItemListCache().notExist();
            }else {
                seckillItemListCache = loadSeckillItemListFromDb(seckillId);
            }
        }

        //3. 如果分布式缓存中存在列表，或者从数据库读出了列表，那么就需要更新本地缓存
        if(!seckillItemListCache.isLater()) {
            //3.1 此处使用ReentrantLock加锁进行更新，防止并发更新下产生安全问题
            boolean isLockSuccess = localSeckillItemListCacheUpdatelock.tryLock();
            if(isLockSuccess) {
                try{
                    seckillItemListGuavaCache.put(seckillId, seckillItemListCache);
                    log.info(LogFormatUtil.format(this, "更新了本地秒杀商品缓存", seckillId));
                }finally {
                    localSeckillItemListCacheUpdatelock.unlock();
                }
            }
        }

        return seckillItemListCache;

    }

    private SeckillItemListCache loadSeckillItemListFromDb(Long seckillId) {
        log.info(LogFormatUtil.format(this, "从数据库获取秒杀商品列表", null));

        //1. 从数据库中获取列表数据需要加分布式锁，防止多个微服务同时去数据库中获取数据更新缓存造成数据错误的问题
        DistributedLock lock = distributedLockFactory.getDistributedLock(RedisPrefixConstants.Marketing.LOCK_GET_SECKILL_ITEM_LIST_FROM_DB + seckillId);
        try{
            boolean lockFlag = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if(!lockFlag) {
                return new SeckillItemListCache().tryLater();
            }

            //2. 从数据库中获取秒杀品列表
            List<SeckillItem> seckillItemList = seckillItemService.list(Wrappers.<SeckillItem>lambdaQuery().eq(SeckillItem::getSeckillId, seckillId));
            SeckillItemListCache seckillItemListCache;
            if(seckillItemList.isEmpty()) {
                //3. 如果数据库中不存在数据列表，则返回无记录对象
                seckillItemListCache = new SeckillItemListCache().notExist();
            }else {
                //4. 如果数据库中存在记录，则返回有记录对象，并设置版本号，版本号为当前时间的时间戳
                seckillItemListCache = new SeckillItemListCache()
                        .setTotal(seckillItemList.size())
                        .setSeckillItemList(seckillItemList)
                        .setVersion(System.currentTimeMillis());
            }

            //5. 将数据库中获取的数据列表回写到Redis，并设置失效时间为5分钟，具体失效时间根据业务而定
            redisUtils.setEx(RedisPrefixConstants.Marketing.CACHE_SECKILL_ITEM_LIST, JsonUtil.toJson(seckillItemListCache), 5, TimeUnit.MINUTES);
            log.info(LogFormatUtil.format(this, "从数据库获取秒杀商品列表,更新分布式缓存成功", seckillId));
            return seckillItemListCache;
        }catch (Exception e) {
            log.error(LogFormatUtil.format(this, "从数据库获取秒杀商品列表,更新分布式缓存失败", seckillId), e);

            //6. 如果抛出异常，则返回一个稍后再试的缓存对象
            return new SeckillItemListCache().tryLater();
        }finally {
            lock.unlock();
        }

    }
}
