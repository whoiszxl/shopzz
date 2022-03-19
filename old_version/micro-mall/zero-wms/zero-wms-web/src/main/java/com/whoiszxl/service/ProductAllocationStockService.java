package com.whoiszxl.service;

import com.whoiszxl.entity.ProductAllocationStock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 货位库存表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
public interface ProductAllocationStockService extends IService<ProductAllocationStock> {

    /**
     * 获取商品货位库存，如果不存在则新建一条空记录
     * @param productAllocationId 商品货位ID
     * @param productSkuId 商品SKU ID
     * @return 商品货位库存
     */
    ProductAllocationStock getOrSave(Long productAllocationId, Long productSkuId);

    /**
     * 减去可用库存，加上锁定库存
     * @param pickingCount 拣货的数量
     * @param productAllocationId 货位id
     * @param skuId sku id
     * @return 是否更新成功
     */
    boolean subAvailableStockAndAddLockedStock(Integer pickingCount, Long productAllocationId, Long skuId);

    /**
     * 减去锁定库存，加上出库库存
     * @param pickingCount 拣货数量
     * @param productAllocationId 商品货位id
     * @param skuId sku id
     * @return 是否更新成功
     */
    boolean subLockedStockAndAddDeliveriedStock(Integer pickingCount, Long productAllocationId, Long skuId);
}
