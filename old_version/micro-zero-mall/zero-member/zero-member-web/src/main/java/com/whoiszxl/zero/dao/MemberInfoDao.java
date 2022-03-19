package com.whoiszxl.zero.dao;

import com.whoiszxl.zero.entity.MemberInfo;

public interface MemberInfoDao {

    /**
     * 通过会员Id查找会员详情
     * @param memberId 会员ID
     * @return
     */
    MemberInfo findByMemberId(Long memberId);
}
