package com.whoiszxl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.entity.SkuSaleAttributeValue;
import com.whoiszxl.entity.vo.SkuDetailSaleAttributeVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * sku销售属性值表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
public interface SkuSaleAttributeValueMapper extends BaseMapper<SkuSaleAttributeValue> {

    /**
     * 通过商品ID获取商品下的所有销售属性
     * @param productId 商品ID
     * @return 销售属性集合
     */
    @Select("SELECT " +
            "attribute_id as attributeId," +
            "attribute_name as attributeName," +
            "attribute_value as attributeValue," +
            "GROUP_CONCAT(ps.id) as skuIds " +
            "FROM pms_sku ps " +
            "LEFT JOIN pms_sku_sale_attribute_value ssav ON ps.id=ssav.sku_id " +
            "WHERE ps.product_id = #{productId} " +
            "GROUP BY ssav.attribute_id,ssav.attribute_name,ssav.attribute_value")
    List<SkuDetailSaleAttributeVO> listSaleAttrs(Long productId);
}
