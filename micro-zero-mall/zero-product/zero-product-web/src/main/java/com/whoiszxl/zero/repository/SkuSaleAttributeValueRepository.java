package com.whoiszxl.zero.repository;

import com.whoiszxl.zero.bean.BaseRepository;
import com.whoiszxl.zero.entity.SkuSaleAttributeValue;
import com.whoiszxl.zero.entity.dto.impl.SkuDetailSaleAttributeInterface;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 销售属性repository
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
public interface SkuSaleAttributeValueRepository extends BaseRepository<SkuSaleAttributeValue> {

    @Query(value = "SELECT " +
            "attribute_id as attributeId," +
            "attribute_name as attributeName," +
            "attribute_value as attributeValue," +
            "GROUP_CONCAT(ps.id) as skuIds " +
            "FROM pms_sku ps " +
            "LEFT JOIN pms_sku_sale_attribute_value ssav ON ps.id=ssav.sku_id " +
            "WHERE ps.product_id = :productId " +
            "GROUP BY ssav.attribute_id,ssav.attribute_name,ssav.attribute_value",
            nativeQuery = true
    )
    public List<SkuDetailSaleAttributeInterface> listSaleAttrs(@Param("productId") Long productId);
}
