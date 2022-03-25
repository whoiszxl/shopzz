package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.WarehouseSkuStock;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
public interface WarehouseSkuStockService extends IService<WarehouseSkuStock> {

    /**
     * 通过商品SKU ID查找商品库存信息，如果不存在则新建
     * @param skuId 商品SKU ID
     * @return 商品库存信息
     */
    WarehouseSkuStock getOrSaveBySkuId(Long skuId);
}
