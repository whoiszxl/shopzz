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
}
