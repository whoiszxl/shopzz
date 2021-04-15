package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.SkuDao;
import com.whoiszxl.zero.entity.Sku;
import com.whoiszxl.zero.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SKU dao接口实现
 *
 * @author whoiszxl
 * @date 2021/4/14
 */
@Repository
public class SkuDaoImpl implements SkuDao {

    @Autowired
    private SkuRepository skuRepository;

    @Override
    public List<Sku> findAllByProductId(Long productId) {
        return skuRepository.findAllByProductId(productId);
    }
}
