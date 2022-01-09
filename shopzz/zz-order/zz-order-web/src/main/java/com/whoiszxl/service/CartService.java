package com.whoiszxl.service;

import com.whoiszxl.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.query.AddCartQuery;

/**
 * <p>
 * 购物车记录表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-01-09
 */
public interface CartService extends IService<Cart> {

    /**
     * 将商品添加到购物车中
     * @param addCartQuery 加购参数
     * @return
     */
    Boolean cartAdd(AddCartQuery addCartQuery);

}
