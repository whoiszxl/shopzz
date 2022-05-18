package com.whoiszxl.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.cache.StockCache;
import com.whoiszxl.entity.SeckillItem;
import com.whoiszxl.service.SeckillItemService;
import com.whoiszxl.service.StockCacheService;
import com.whoiszxl.utils.AssertUtils;
import com.whoiszxl.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 默认库存缓存服务接口实现
 *
 * @author whoiszxl
 * @date 2022/5/18
 */
@Slf4j
@Service
public class DefaultStockCacheServiceImpl implements StockCacheService {

    private final static Cache<Long, StockCache> stockCacheCache
            = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .concurrencyLevel(5)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SeckillItemService seckillItemService;

    /** 库存扣减LUA脚本 */
    private static final String SUB_STOCK_LUA;

    /** 库存增加LUA脚本 */
    private static final String ADD_STOCK_LUA;

    /** 初始化库存LUA脚本 */
    private static final String INIT_STOCK_LUA;

    static {
        //1. 判断第二个参数是否存在，存在则返回-9，表示还在校准中
        //2. 判断第一个参数是否存在, 存在则执行逻辑
        //3. 通过第一个参数拿到库存
        //4. 判断库存是否小于扣减数量，小于则返回-3
        //5. 库存大于等于扣减数量，则调用incrby进行扣减操作
        SUB_STOCK_LUA = "if (redis.call('exists', KEYS[2]) == 1) then" +
                "    return -9;" +
                "end;" +
                "if (redis.call('exists', KEYS[1]) == 1) then" +
                "    local stock = tonumber(redis.call('get', KEYS[1]));" +
                "    local num = tonumber(ARGV[1]);" +
                "    if (stock < num) then" +
                "        return -3" +
                "    end;" +
                "    if (stock >= num) then" +
                "        redis.call('incrby', KEYS[1], 0 - num);" +
                "        return 1" +
                "    end;" +
                "    return -2;" +
                "end;" +
                "return -1;";

        // 对keys[1] 调用 incrby 进行累加
        ADD_STOCK_LUA = "if (redis.call('exists', KEYS[2]) == 1) then" +
                "    return -9;" +
                "end;" +
                "if (redis.call('exists', KEYS[1]) == 1) then" +
                "    local stock = tonumber(redis.call('get', KEYS[1]));" +
                "    local num = tonumber(ARGV[1]);" +
                "    redis.call('incrby', KEYS[1] , num);" +
                "    return 1;" +
                "end;" +
                "return -1;";

        // 通过预热键进行加锁，初始化后再删除预热键
        INIT_STOCK_LUA = "if (redis.call('exists', KEYS[2]) == 1) then" +
                "    return -997;" +
                "end;" +
                "redis.call('set', KEYS[2] , 1);" +
                "local stockNumber = tonumber(ARGV[1]);" +
                "redis.call('set', KEYS[1] , stockNumber);" +
                "redis.call('del', KEYS[2]);" +
                "return 1";
    }

    @Override
    public StockCache getAvailableStock(Long itemId) {
        //从本地缓存中获取到item的库存
        StockCache stockCache = stockCacheCache.getIfPresent(itemId);
        if(stockCache != null) {
            return stockCache;
        }

        //如果不存在，则从redis中获取
        Object stockQuantityObj = redisUtils.getObj(RedisKeyPrefixConstants.CACHE_ITEM_STOCK + itemId);
        if(stockQuantityObj == null) {
            return null;
        }

        Integer stockQuantity = (Integer) stockQuantityObj;
        stockCache = new StockCache().with(stockQuantity);

        return stockCache;
    }

    @Override
    public boolean subCacheStock(Long seckillItemId, Integer quantity) {
        try{
            log.info("开始预扣减库存, seckillItemId:{}, quantity:{}", seckillItemId, quantity);

            //构建lua脚本
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(SUB_STOCK_LUA, Long.class);
            String stockCacheKey = "cache:stock:" + seckillItemId;
            String stockCacheAlignKey = "cache:align-stock:" + seckillItemId;
            List<String> keys = Lists.newArrayList(stockCacheKey, stockCacheAlignKey);

            long startTime = System.currentTimeMillis();

            Long result = null;
            //循环调用扣减库存lua脚本，如果在校准中则重复执行，超时时间1.5秒
            while((result == null || result == -9) && (System.currentTimeMillis() - startTime) < 1500) {
                result = (Long) redisUtils.execute(redisScript, keys, quantity);
                if (result == null) {
                    log.info("库存扣减失败，result=null");
                    return false;
                }
                if (result == -9) {
                    log.info("库存校准中");
                    Thread.sleep(20);
                }
                if (result == -1 || result == -2) {
                    log.info("库存扣减失败，result == -1 || result == -2");
                    return false;
                }
                if (result == -3) {
                    log.info("库存扣减失败，result == -3");
                    return false;
                }
                if (result == 1) {
                    log.info("库存扣减成功");
                    return true;
                }
            }
        }catch (Exception e) {
            log.error("库存扣减失败：", e);
            return false;
        }

        return false;
    }

    @Override
    public boolean addCacheStock(Long seckillItemId, Integer quantity) {
        try{
            log.info("开始增加库存, seckillItemId:{}, quantity:{}", seckillItemId, quantity);

            //构建lua脚本
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(ADD_STOCK_LUA, Long.class);
            String stockCacheKey = "cache:stock:" + seckillItemId;
            String stockCacheAlignKey = "cache:align-stock:" + seckillItemId;
            List<String> keys = Lists.newArrayList(stockCacheKey, stockCacheAlignKey);

            long startTime = System.currentTimeMillis();

            Long result = null;
            //循环调用扣减库存lua脚本，如果在校准中则重复执行，超时时间1.5秒
            while((result == null || result == -9) && (System.currentTimeMillis() - startTime) < 1500) {
                result = (Long) redisUtils.execute(redisScript, keys, quantity);
                if (result == null) {
                    log.info("库存增加失败，result=null");
                    return false;
                }
                if (result == -9) {
                    log.info("库存校准中");
                    Thread.sleep(20);
                }
                if (result == -1) {
                    log.info("库存扣减失败，result == -1");
                    return false;
                }
                if (result == 1) {
                    log.info("库存增加成功");
                    return true;
                }
            }
        }catch (Exception e) {
            log.error("库存增加失败：", e);
            return false;
        }

        return false;
    }

    @Override
    public boolean initItemStock(Long seckillItemId) {
        try {
            SeckillItem seckillItem = seckillItemService.getById(seckillItemId);
            if(seckillItem == null) {
                log.info("秒杀商品不存在, id:{}", seckillItemId);
                return false;
            }

            if(seckillItem.getInitStockQuantity() < 1) {
                log.info("秒杀商品库存未配置, id:{}", seckillItemId);
                return false;
            }

            //构建lua脚本
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(SUB_STOCK_LUA, Long.class);
            String stockCacheKey = "cache:stock:" + seckillItemId;
            String stockCacheAlignKey = "cache:align-stock:" + seckillItemId;
            List<String> keys = Lists.newArrayList(stockCacheKey, stockCacheAlignKey);

            Long result = (Long) redisUtils.execute(redisScript, keys, seckillItem.getInitStockQuantity());
            if(result == null) {
                log.info("库存初始化失败，id:{}", seckillItemId);
                return false;
            }

            if(result == -997) {
                log.info("库存正在初始化中，本次初始化取消，id:{}", seckillItemId);
                return true;
            }

            if (result == 1) {
                log.info("库存初始化完成， id:{}", seckillItemId);
                return true;
            }
            return false;
        }catch (Exception e) {
            log.error("初始化库存发生异常：{}", e);
            return false;
        }
    }
}
