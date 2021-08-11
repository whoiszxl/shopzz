package com.whoiszxl.service;

import com.whoiszxl.entity.ProductStock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 库存中心的商品库存表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-21
 */
public interface ProductStockService extends IService<ProductStock> {

    /**
     * 通过商品SKUID获取商品库存信息
     * @param skuId 商品SKUID
     * @return
     */
    ProductStock getProductStockBySkuId(Long skuId);

    /**
     * 更新商品库存
     * @param productStock 商品库存对象
     */
    void updateProductStock(ProductStock productStock);

    /**
     * 减去销售库存，加上锁定库存，通过SKUID
     * @param quantity 操作数量
     * @param skuId SKU ID
     * @return 是否更新成功
     */
    boolean subSaleStockAndAddLockStockBySkuId(Integer quantity, Long skuId);

    /**
     * 增加销售库存
     * @param purchaseQuantity 入库数量
     * @param productSkuId skuID
     * @return 是否新增成功
     */
    boolean addSaleStock(Integer purchaseQuantity, Long productSkuId);

    /**
     * 通过SKU ID 减去锁定库存,增加已销售库存
     * @param quantity
     * @param skuId
     * @return
     */
    boolean subLockStockAndAddSaledStockBySkuId(Integer quantity, Long skuId);
}
