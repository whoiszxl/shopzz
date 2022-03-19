package com.whoiszxl.zero.repository;

import com.whoiszxl.zero.bean.BaseRepository;
import com.whoiszxl.zero.entity.MemberAddress;

import java.util.List;

public interface MemberAddressRepository extends BaseRepository<MemberAddress> {

    /**
     * 通过会员ID查找用户收货地址列表
     * @param memberId 会员ID
     * @return
     */
    List<MemberAddress> findAllByMemberIdOrderByIsDefaultDesc(Long memberId);

    /**
     * 判断当前地址是否是当前用户的
     * @param id
     * @param memberId
     * @return
     */
    MemberAddress findByIdAndMemberId(Long id, Long memberId);

}
