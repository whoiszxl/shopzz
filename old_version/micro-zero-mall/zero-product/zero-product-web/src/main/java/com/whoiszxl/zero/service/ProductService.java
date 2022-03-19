package com.whoiszxl.zero.service;

import com.whoiszxl.zero.bean.MyPage;
import com.whoiszxl.zero.entity.Product;
import com.whoiszxl.zero.entity.params.SearchParams;
import com.whoiszxl.zero.entity.vo.ProductDetailVO;

public interface ProductService {

    /**
     * 分页搜索商品列表
     * @param searchParams
     * @return
     */
    MyPage search(SearchParams searchParams);

    /**
     * 通过商品SPU ID查找商品详情
     * @param productId
     * @return
     */
    ProductDetailVO detail(Long productId);
}
