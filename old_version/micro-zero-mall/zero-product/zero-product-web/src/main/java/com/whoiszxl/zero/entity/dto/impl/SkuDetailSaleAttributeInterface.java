package com.whoiszxl.zero.entity.dto.impl;

/**
 * 销售属性组接口 JPA自定义结果集用
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
public interface SkuDetailSaleAttributeInterface {

    Long getAttributeId();

    String getAttributeName();

    String getAttributeValue();

    String getSkuIds();
}
