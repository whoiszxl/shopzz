package com.whoiszxl.db.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.aggregate.model.Member;
import com.whoiszxl.aggregate.repository.MemberRepository;
import com.whoiszxl.db.converter.MemberConverter;
import com.whoiszxl.db.mapper.MemberInfoMapper;
import com.whoiszxl.db.mapper.MemberMapper;
import com.whoiszxl.db.model.MemberInfoPO;
import com.whoiszxl.db.model.MemberPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 用户领域仓储服务实现
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private MemberConverter memberConverter;

    @Override
    public Member byUsername(String username) {
        MemberPO memberPO = memberMapper.selectOne(Wrappers.<MemberPO>lambdaQuery().eq(MemberPO::getUsername,username));
        if(Objects.isNull(memberPO)){
            return null;
        }
        return memberConverter.poToDomain(memberPO);
    }

    @Override
    public void delete(Long id) {
        memberMapper.deleteById(id);
        memberInfoMapper.delete(Wrappers.<MemberInfoPO>lambdaQuery().eq(MemberInfoPO::getMemberId, id));
    }

    @Override
    public Member byId(Long id) {
        MemberPO memberPO = memberMapper.selectById(id);
        if(Objects.isNull(memberPO)) {
            return null;
        }
        return memberConverter.poToDomain(memberPO);
    }

    @Override
    public Member save(Member member) {
        MemberPO memberPO = memberConverter.domainToPo(member);
        memberMapper.insert(memberPO);
        member.setId(memberPO.getId());
        return member;
    }

    @Override
    public Member update(Member member) {
        MemberPO memberPO = memberConverter.domainToPo(member);
        memberMapper.updateById(memberPO);
        return member;
    }

}
