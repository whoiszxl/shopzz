package com.whoiszxl.service;

import com.whoiszxl.cqrs.cache.SeckillCache;

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
}
