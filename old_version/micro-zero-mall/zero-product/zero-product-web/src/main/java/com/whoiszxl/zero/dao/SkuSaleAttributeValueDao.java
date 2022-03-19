package com.whoiszxl.zero.dao;

import com.whoiszxl.zero.entity.dto.impl.SkuDetailSaleAttributeInterface;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 销售属性dao
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
public interface SkuSaleAttributeValueDao {

    /**
     * 查询销售属性数据
     * @param productId 商品ID
     * @return
     */
    public List<SkuDetailSaleAttributeInterface> listSaleAttrs(@Param("productId") Long productId);
}
