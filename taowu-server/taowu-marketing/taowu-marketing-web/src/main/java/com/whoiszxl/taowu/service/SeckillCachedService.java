package com.whoiszxl.taowu.service;

import com.whoiszxl.taowu.cqrs.cache.SeckillCache;
import com.whoiszxl.taowu.cqrs.cache.SeckillItemListCache;
import com.whoiszxl.taowu.cqrs.cache.SeckillListCache;

/**
 * 秒杀缓存服务接口
 */
public interface SeckillCachedService {

    /**
     * 从缓存中获取秒杀商品
     * @param seckillItemId
     * @param version
     * @return
     */
    SeckillCache getCachedSeckill(Long seckillId, Long version);

    /**
     * 从缓存中获取秒杀活动列表
     * @param version 版本号
     * @return
     */
    SeckillListCache getCachedSeckillList(Long version);

    /**
     * 从缓存中获取秒杀活动下的商品列表
     * @param seckillId
     * @param version
     * @return
     */
    SeckillItemListCache getCachedSeckillItemList(Long seckillId, Long version);
}
