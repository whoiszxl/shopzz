package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.Product;
import com.whoiszxl.entity.vo.CustomProductDetailVO;

/**
 * <p>
 * 商品基础信息表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
public interface ProductService extends IService<Product> {

    /**
     * 通过商品ID获取商品详情
     * @param productId 商品ID
     * @return 商品详细信息
     */
    CustomProductDetailVO detail(Long productId);
}
