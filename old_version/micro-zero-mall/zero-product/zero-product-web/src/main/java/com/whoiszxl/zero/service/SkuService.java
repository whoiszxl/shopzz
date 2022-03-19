package com.whoiszxl.zero.service;

import com.whoiszxl.zero.dto.SkuDTO;

import java.util.List;

public interface SkuService {

    /**
     * 通过商品ID查找商品下的所有SKU信息
     * @param productId 商品ID
     * @return
     */
    List<SkuDTO> findAllByProductId(Long productId);


    /**
     * 通过主键查找SKU信息
     * @param skuId 主键ID
     * @return
     */
    SkuDTO findById(Long skuId);
}
