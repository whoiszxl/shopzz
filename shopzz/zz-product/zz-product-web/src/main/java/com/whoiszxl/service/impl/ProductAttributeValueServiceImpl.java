package com.whoiszxl.service.impl;

import com.whoiszxl.entity.ProductAttributeValue;
import com.whoiszxl.entity.vo.SpuDetailAttrGroupVo;
import com.whoiszxl.mapper.ProductAttributeValueMapper;
import com.whoiszxl.service.ProductAttributeValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性值表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Service
public class ProductAttributeValueServiceImpl extends ServiceImpl<ProductAttributeValueMapper, ProductAttributeValue> implements ProductAttributeValueService {

    @Autowired
    private ProductAttributeValueMapper productAttributeValueMapper;

    @Override
    public List<SpuDetailAttrGroupVo> getProductGroupAttrsBySpuId(Long id, Long categoryId) {
        return productAttributeValueMapper.getProductGroupAttrsBySpuId(id, categoryId);
    }
}
