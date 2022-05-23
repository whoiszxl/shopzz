package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.SeckillItem;

/**
 * <p>
 * 秒杀item表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
public interface SeckillItemService extends IService<SeckillItem> {

    /**
     * 库存实际DB扣减
     * @param seckillItemId
     * @param quantity
     * @return
     */
    boolean subDbStock(Long seckillItemId, Integer quantity);

    /**
     * 库存实际DB增加
     * @param seckillItemId
     * @param quantity
     * @return
     */
    boolean addDbStock(Long seckillItemId, Integer quantity);
}
