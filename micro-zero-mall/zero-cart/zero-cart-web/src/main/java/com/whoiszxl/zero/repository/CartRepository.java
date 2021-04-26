package com.whoiszxl.zero.repository;

import com.whoiszxl.zero.bean.BaseRepository;
import com.whoiszxl.zero.entity.Cart;

public interface CartRepository extends BaseRepository<Cart> {


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
