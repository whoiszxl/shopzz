package com.whoiszxl.service;

import com.whoiszxl.entity.DispatchWarehouseProductStock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 调度中心商品库存表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
public interface DispatchWarehouseProductStockService extends IService<DispatchWarehouseProductStock> {

    /**
     * 根据商品SKU ID获取调度库存信息，如果不存在则创建
     * @param productSkuId 商品SKU ID
     * @return 调度库存信息
     */
    DispatchWarehouseProductStock getOrSave(Long productSkuId);
}
