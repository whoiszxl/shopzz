package com.whoiszxl.mapper;

import com.whoiszxl.entity.ProductAttributeValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.entity.vo.SpuDetailAttrGroupVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 商品属性值表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
public interface ProductAttributeValueMapper extends BaseMapper<ProductAttributeValue> {

    @Select("SELECT ag.group_name as groupName, " +
            "attr.attribute_id as attributeId, " +
            "attr.attribute_name as attributeName ," +
            "attr.attribute_value as attributeValue " +
            "FROM pms_attribute_attrgroup_relation aar " +
            "LEFT JOIN pms_attribute_group ag ON aar.attribute_group_id = ag.id " +
            "LEFT JOIN pms_product_attribute_value attr ON aar.attribute_id = attr.attribute_id " +
            "WHERE attr.product_id = #{productId} AND ag.category_id = #{categoryId}")
    List<SpuDetailAttrGroupVo> getProductGroupAttrsBySpuId(Long productId, Long categoryId);
}
