package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.SkuSaleAttributeValueDao;
import com.whoiszxl.zero.entity.dto.impl.SkuDetailSaleAttributeInterface;
import com.whoiszxl.zero.repository.SkuSaleAttributeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 销售属性dao实现
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
@Repository
public class SkuSaleAttributeValueDaoImpl implements SkuSaleAttributeValueDao {

    @Autowired
    private SkuSaleAttributeValueRepository skuSaleAttributeValueRepository;

    @Override
    public List<SkuDetailSaleAttributeInterface> listSaleAttrs(Long productId) {
        return skuSaleAttributeValueRepository.listSaleAttrs(productId);
    }
}
