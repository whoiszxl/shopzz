package com.whoiszxl.zero.repository;

import com.whoiszxl.zero.bean.BaseRepository;
import com.whoiszxl.zero.entity.Cart;

import java.util.List;

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

    /**
     * 通过用户ID和是否选中获取购物车内容
     * @param memberId 用户ID
     * @param checked 是否选中
     * @return
     */
    List<Cart> findAllByMemberIdAndChecked(Long memberId, Integer checked);
}
