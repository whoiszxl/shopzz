package com.whoiszxl.zero.dao;

import com.whoiszxl.zero.entity.ProductImages;

import java.util.List;

/**
 * 商品图片dao接口
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
public interface ProductImagesDao {

    /**
     * 通过商品ID查询商品图片列表
     * @param productId 商品ID
     * @return
     */
    List<ProductImages> findAllByProductIdOOrderBySortDesc(Long productId);
}
