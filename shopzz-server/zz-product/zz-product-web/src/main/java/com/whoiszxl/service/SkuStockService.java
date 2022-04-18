package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.SkuStock;

/**
 * <p>
 * 商品SKU库存表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
public interface SkuStockService extends IService<SkuStock> {

    /**
     * 扣减库存
     * @param skuId sku id
     * @param quantity 扣减数量
     * @return
     */
    int subSaleStockAndAddLockStockBySkuId(Long skuId, Integer quantity);

    /**
     * 通过SKU ID 减去锁定库存,增加已销售库存
     * @param quantity
     * @param skuId
     * @return
     */
    boolean subLockStockAndAddSaledStockBySkuId(Integer quantity, Long skuId);
}
