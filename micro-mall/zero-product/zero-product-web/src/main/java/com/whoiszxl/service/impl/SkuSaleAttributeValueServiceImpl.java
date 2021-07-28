package com.whoiszxl.service.impl;

import com.whoiszxl.entity.SkuSaleAttributeValue;
import com.whoiszxl.entity.vo.SkuDetailSaleAttributeVO;
import com.whoiszxl.mapper.SkuSaleAttributeValueMapper;
import com.whoiszxl.service.SkuSaleAttributeValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku销售属性值表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Service
public class SkuSaleAttributeValueServiceImpl extends ServiceImpl<SkuSaleAttributeValueMapper, SkuSaleAttributeValue> implements SkuSaleAttributeValueService {

    @Autowired
    private SkuSaleAttributeValueMapper skuSaleAttributeValueMapper;

    @Override
    public List<SkuDetailSaleAttributeVO> listSaleAttrs(Long productId) {
        return skuSaleAttributeValueMapper.listSaleAttrs(productId);
    }
}
