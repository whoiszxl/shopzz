package com.whoiszxl.service;

import com.whoiszxl.cqrs.cache.SeckillItemCache;

/**
 * 秒杀缓存服务接口
 */
public interface SeckillItemCachedService {

    /**
     * 从缓存中获取秒杀商品item
     * @param seckillItemId
     * @param version
     * @return
     */
    SeckillItemCache getCachedSeckillItem(Long seckillItemId, Long version);
}
