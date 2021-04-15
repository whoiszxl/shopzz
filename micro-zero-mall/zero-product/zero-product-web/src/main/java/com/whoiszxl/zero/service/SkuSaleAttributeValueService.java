package com.whoiszxl.zero.service;

import com.whoiszxl.zero.entity.dto.impl.SkuDetailSaleAttributeInterface;

import java.util.List;

/**
 * 商品销售属性服务
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
public interface SkuSaleAttributeValueService {

    /**
     * 通过商品ID获取商品下的所有销售属性
     * @param productId
     * @return
     */
    List<SkuDetailSaleAttributeInterface> listSaleAttrs(Long productId);
}
