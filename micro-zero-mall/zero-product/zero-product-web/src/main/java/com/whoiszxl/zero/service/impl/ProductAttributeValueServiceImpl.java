package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.ProductAttributeValueDao;
import com.whoiszxl.zero.entity.dto.impl.SpuDetailAttrGroupInterface;
import com.whoiszxl.zero.service.ProductAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品基础属性服务实现
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
@Service
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService {

    @Autowired
    private ProductAttributeValueDao productAttributeValueDao;

    @Override
    public List<SpuDetailAttrGroupInterface> getProductGroupAttrsBySpuId(Long productId, Long categoryId) {
        return productAttributeValueDao.getProductGroupAttrsBySpuId(productId, categoryId);
    }
}
