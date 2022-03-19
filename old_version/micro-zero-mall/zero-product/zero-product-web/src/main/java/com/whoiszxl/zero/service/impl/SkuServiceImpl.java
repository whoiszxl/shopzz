package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.SkuDao;
import com.whoiszxl.zero.dto.SkuDTO;
import com.whoiszxl.zero.entity.Sku;
import com.whoiszxl.zero.service.SkuService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SKU服务接口实现
 *
 * @author whoiszxl
 * @date 2021/4/14
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuDao skuDao;

    @Override
    public List<SkuDTO> findAllByProductId(Long productId) {
        return BeanCopierUtils.copyListProperties(skuDao.findAllByProductId(productId), SkuDTO::new);
    }

    @Override
    public SkuDTO findById(Long skuId) {
        Sku sku = skuDao.findById(skuId);
        return sku == null ? null : sku.clone(SkuDTO.class);
    }
}
