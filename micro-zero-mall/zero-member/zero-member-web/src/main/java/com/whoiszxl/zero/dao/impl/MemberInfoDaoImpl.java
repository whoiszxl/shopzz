package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.MemberInfoDao;
import com.whoiszxl.zero.entity.MemberInfo;
import com.whoiszxl.zero.repository.MemberInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberInfoDaoImpl implements MemberInfoDao {

    @Autowired
    private MemberInfoRepository memberInfoRepository;

    @Override
    public MemberInfo findByMemberId(Long memberId) {
        return memberInfoRepository.findByMemberId(memberId);
    }
}
