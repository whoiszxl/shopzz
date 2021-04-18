package com.whoiszxl.zero.repository;

import com.whoiszxl.zero.bean.BaseRepository;
import com.whoiszxl.zero.entity.MemberInfo;

public interface MemberInfoRepository extends BaseRepository<MemberInfo> {

    /**
     * 通过会员Id查找会员详情
     * @param memberId 会员ID
     * @return
     */
    MemberInfo findByMemberId(Long memberId);

}
