package com.whoiszxl.zero.entity.dto.impl;

/**
 * 基础属性组接口 JPA自定义结果集用
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
public interface SpuDetailAttrGroupInterface {

    String getGroupName();

    Long getAttributeId();

    String getAttributeName();

    String getAttributeValue();

}