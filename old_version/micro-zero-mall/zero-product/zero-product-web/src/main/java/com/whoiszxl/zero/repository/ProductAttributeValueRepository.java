package com.whoiszxl.zero.repository;

import com.whoiszxl.zero.bean.BaseRepository;
import com.whoiszxl.zero.entity.ProductAttributeValue;
import com.whoiszxl.zero.entity.dto.impl.SpuDetailAttrGroupInterface;
import com.whoiszxl.zero.entity.vo.SpuDetailAttrGroupVo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 基础属性repository
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
public interface ProductAttributeValueRepository extends BaseRepository<ProductAttributeValue> {

    /**
     * 通过商品Id和商品的三级分类Id获取基础属性分组信息
     * @param productId 商品ID
     * @param categoryId 商品所属三级分类ID
     * @return
     */
    @Query(value = "SELECT ag.group_name as groupName, " +
            "attr.attribute_id as attributeId, " +
            "attr.attribute_name as attributeName ," +
            "attr.attribute_value as attributeValue " +
            "FROM pms_attribute_attrgroup_relation aar " +
            "LEFT JOIN pms_attribute_group ag ON aar.attribute_group_id = ag.id " +
            "LEFT JOIN pms_product_attribute_value attr ON aar.attribute_id = attr.attribute_id " +
            "WHERE attr.product_id = :productId AND ag.category_id = :categoryId", nativeQuery = true)
    List<SpuDetailAttrGroupInterface> getProductGroupAttrsBySpuId(
            @Param("productId") Long productId,
            @Param("categoryId") Long categoryId);

}
