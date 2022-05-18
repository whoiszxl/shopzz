package com.whoiszxl.service;

import com.whoiszxl.cqrs.cache.StockCache;

/**
 * 库存缓存服务接口
 *
 * @author whoiszxl
 * @date 2022/5/18
 */
public interface StockCacheService {

    /**
     * 获取到可用库存
     * @param itemId
     * @return
     */
    StockCache getAvailableStock(Long itemId);

    /**
     * 预扣库存
     * @param seckillItemId
     * @param quantity
     * @return
     */
    boolean subCacheStock(Long seckillItemId, Integer quantity);

    /**
     * 增加库存
     * @param seckillItemId
     * @param quantity
     * @return
     */
    boolean addCacheStock(Long seckillItemId, Integer quantity);

    /**
     * 初始化秒杀商品库存
     * @param seckillItemId
     * @return
     */
    boolean initItemStock(Long seckillItemId);
}
