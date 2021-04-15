package com.whoiszxl.zero.repository;

import com.whoiszxl.zero.bean.BaseRepository;
import com.whoiszxl.zero.entity.ProductImages;

import java.util.List;

/**
 * 商品图片repository
 *
 * @author whoiszxl
 * @date 2021/4/12
 */
public interface ProductImagesRepository extends BaseRepository<ProductImages> {

    /**
     * 通过商品ID查询商品图片列表
     * @param productId 商品ID
     * @return
     */
    List<ProductImages> findAllByProductIdOrderBySortDesc(Long productId);
}
