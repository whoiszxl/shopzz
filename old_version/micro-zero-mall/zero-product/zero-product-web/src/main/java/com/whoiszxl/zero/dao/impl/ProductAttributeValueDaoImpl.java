package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.ProductAttributeValueDao;
import com.whoiszxl.zero.entity.dto.impl.SpuDetailAttrGroupInterface;
import com.whoiszxl.zero.repository.ProductAttributeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品基础属性dao
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
@Repository
public class ProductAttributeValueDaoImpl implements ProductAttributeValueDao {

    @Autowired
    private ProductAttributeValueRepository productAttributeValueRepository;

    @Override
    public List<SpuDetailAttrGroupInterface> getProductGroupAttrsBySpuId(Long productId, Long categoryId) {
        return productAttributeValueRepository.getProductGroupAttrsBySpuId(productId, categoryId);
    }
}
