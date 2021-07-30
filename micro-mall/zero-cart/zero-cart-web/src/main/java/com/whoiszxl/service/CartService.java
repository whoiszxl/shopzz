package com.whoiszxl.service;

import com.whoiszxl.dto.CartDTO;
import com.whoiszxl.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.query.CartAddQuery;

/**
 * <p>
 * 购物车记录表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
public interface CartService extends IService<Cart> {

    /**
     * 添加一个商品到购物车中
     * @param cartAddQuery 添加参数
     * @return 是否添加成功
     */
    boolean addProductToCart(CartAddQuery cartAddQuery);

    /**
     * 更新购物车数量
     * @param memberId 会员ID
     * @param cartId 购物车ID
     * @param updateQuantity 更新数量
     * @return
     */
    Boolean updateQuantity(long memberId, Long cartId, Integer updateQuantity);
}
