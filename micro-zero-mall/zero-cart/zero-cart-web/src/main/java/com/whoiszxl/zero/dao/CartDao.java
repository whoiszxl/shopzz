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

}
