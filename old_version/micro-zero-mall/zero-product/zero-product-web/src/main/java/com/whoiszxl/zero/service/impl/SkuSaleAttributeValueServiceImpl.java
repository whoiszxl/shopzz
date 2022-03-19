package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.SkuSaleAttributeValueDao;
import com.whoiszxl.zero.entity.dto.impl.SkuDetailSaleAttributeInterface;
import com.whoiszxl.zero.service.SkuSaleAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售属性值服务实现
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
@Service
public class SkuSaleAttributeValueServiceImpl implements SkuSaleAttributeValueService {

    @Autowired
    private SkuSaleAttributeValueDao skuSaleAttributeValueDao;

    @Override
    public List<SkuDetailSaleAttributeInterface> listSaleAttrs(Long productId) {
        return skuSaleAttributeValueDao.listSaleAttrs(productId);
    }
}
