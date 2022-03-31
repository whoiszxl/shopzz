package com.whoiszxl.service;

import com.whoiszxl.cqrs.response.ColumnDetailApiResponse;
import com.whoiszxl.entity.ProductColumn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品专栏表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
public interface ProductColumnService extends IService<ProductColumn> {

    /**
     * 获取专栏详情
     * @param id
     * @return
     */
    ColumnDetailApiResponse detail(Long id);
}
