package com.whoiszxl.zero.dao;

import com.whoiszxl.zero.bean.BaseDao;
import com.whoiszxl.zero.entity.Cart;

public interface CartDao extends BaseDao<Cart, Long> {

    /**
     * 根据用户ID删除购物车所有内容
     * @param memberId 用户ID
     * @return
     */
    int deleteCartsByMemberId(Long memberId);

    /**
     * 根据主键ID删除购物车所有内容
     * @param id 主键ID
     * @return
     */
    int deleteCartByIdAndMemberId(Long id, Long memberId);


    /**
     * 通过主键id和会员id查询购物车详情
     * @return
     */
    Cart findByIdAndMemberIdAndStatus(Long id, Long memberId, Integer status);

    /**
     * 通过sku id和会员id查询购物车详情
     * @return
     */
    Cart findBySkuIdAndMemberIdAndStatus(Long skuId, Long memberId, Integer status);
}
