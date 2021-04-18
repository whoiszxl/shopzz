package com.whoiszxl.zero.dao.impl;

import com.whoiszxl.zero.dao.MemberDao;
import com.whoiszxl.zero.entity.Member;
import com.whoiszxl.zero.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl implements MemberDao {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).get();
    }
}
