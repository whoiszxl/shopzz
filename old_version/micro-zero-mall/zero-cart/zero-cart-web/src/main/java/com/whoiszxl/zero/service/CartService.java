package com.whoiszxl.zero.service;

import com.whoiszxl.zero.dto.CartDTO;
import com.whoiszxl.zero.entity.params.CartAddParam;
import com.whoiszxl.zero.entity.params.CartDeleteParam;
import com.whoiszxl.zero.entity.params.CartQuantityUpdateParam;

import java.util.List;

/**
 * 购物车服务
 */
public interface CartService {

    /**
     * 删除当前用户下购物车所有商品
     * @return
     */
    int deleteAll();

    /**
     * 删除当前用户下购物车里单件商品
     * @param cartDeleteParam
     * @return
     */
    int delete(CartDeleteParam cartDeleteParam);

    /**
     * 更新当前用户下购物车里单件商品的数量
     * @param cartQuantityUpdateParam
     * @return
     */
    int quantityUpdate(CartQuantityUpdateParam cartQuantityUpdateParam);

    /**
     * 将商品添加到购物车里
     * @param cartAddParam
     * @return
     */
    int add(CartAddParam cartAddParam);

    /**
     * 查询当前用户的购物车商品列表
     * @return
     */
    List<CartDTO> list();

    /**
     * 获取用户选中的购物车内容
     * @param memberId 会员ID
     * @return
     */
    List<CartDTO> findAllCheckedCartByMemberId(Long memberId);
}
