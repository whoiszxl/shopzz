package com.whoiszxl.zero.dao;

import com.whoiszxl.zero.entity.Sku;

import java.util.List;

public interface SkuDao {

    /**
     * 通过商品ID查找商品下的所有SKU信息
     * @param productId 商品ID
     * @return
     */
    List<Sku> findAllByProductId(Long productId);

    /**
     * 通过主键查找SKU信息
     * @param skuId 主键ID
     * @return
     */
    Sku findById(Long skuId);
}
