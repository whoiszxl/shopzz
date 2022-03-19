package com.whoiszxl.zero.dao;

import com.whoiszxl.zero.bean.BaseDao;
import com.whoiszxl.zero.entity.MemberAddress;

import java.util.List;

public interface MemberAddressDao extends BaseDao<MemberAddress, Long> {


    /**
     * 通过会员ID查找用户收货地址列表
     * @param memberId 会员ID
     * @return
     */
    List<MemberAddress> findAllByMemberIdOrderByIsDefaultDesc(Long memberId);

    /**
     * 通过id删除
     * @param id
     * @return
     */
    void deleteById(Long id);

    /**
     * 判断当前地址是否是当前用户的
     * @param id
     * @param memberId
     * @return
     */
    MemberAddress findByIdAndMemberId(Long id, Long memberId);
}