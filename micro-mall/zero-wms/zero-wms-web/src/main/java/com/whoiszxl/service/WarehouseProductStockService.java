package com.whoiszxl.service;

import com.whoiszxl.entity.WarehouseProductStock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 仓库商品库存表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
public interface WarehouseProductStockService extends IService<WarehouseProductStock> {

    /**
     * 通过商品SKU ID查找商品库存信息，如果不存在则新建
     * @param productSkuId 商品SKU ID
     * @return 商品库存信息
     */
    WarehouseProductStock getOrSaveBySkuId(Long productSkuId);
}
