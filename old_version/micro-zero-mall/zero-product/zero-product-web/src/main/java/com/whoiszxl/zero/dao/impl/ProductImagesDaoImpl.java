package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.ProductImagesDao;
import com.whoiszxl.zero.entity.ProductImages;
import com.whoiszxl.zero.repository.ProductImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品图片dao接口实现
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
@Repository
public class ProductImagesDaoImpl implements ProductImagesDao {

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Override
    public List<ProductImages> findAllByProductIdOOrderBySortDesc(Long productId) {
        return productImagesRepository.findAllByProductIdOrderBySortDesc(productId);
    }
}
