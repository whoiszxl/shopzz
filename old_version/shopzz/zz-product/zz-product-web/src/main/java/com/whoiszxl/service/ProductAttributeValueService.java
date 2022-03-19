package com.whoiszxl.service;

import com.whoiszxl.entity.ProductAttributeValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.vo.SpuDetailAttrGroupVo;

import java.util.List;

/**
 * <p>
 * 商品属性值表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
public interface ProductAttributeValueService extends IService<ProductAttributeValue> {

    /**
     * 通过商品Id和商品的三级分类Id获取基础属性分组信息
     * @param productId 商品ID
     * @param categoryId 商品所属三级分类ID
     * @return
     */
    List<SpuDetailAttrGroupVo> getProductGroupAttrsBySpuId(Long id, Long categoryId);
}
