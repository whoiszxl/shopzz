package com.whoiszxl.service;

import com.whoiszxl.entity.SkuSaleAttributeValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.vo.SkuDetailSaleAttributeVO;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * sku销售属性值表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
public interface SkuSaleAttributeValueService extends IService<SkuSaleAttributeValue> {

    /**
     * 通过商品ID获取商品下的所有销售属性
     * @param productId 商品ID
     * @return 销售属性集合
     */
    List<SkuDetailSaleAttributeVO> listSaleAttrs(Long productId);
}
