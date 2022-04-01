package com.whoiszxl.db.repository;

import com.whoiszxl.aggregate.model.MemberInfo;
import com.whoiszxl.aggregate.repository.MemberInfoRepository;
import com.whoiszxl.db.converter.MemberInfoConverter;
import com.whoiszxl.db.mapper.MemberInfoMapper;
import com.whoiszxl.db.model.MemberInfoPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 会员详情信息领域仓储服务实现
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Repository
public class MemberInfoRepositoryImpl implements MemberInfoRepository {

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private MemberInfoConverter memberInfoConverter;

    @Override
    public void delete(Long id) {
        memberInfoMapper.deleteById(id);
    }

    @Override
    public MemberInfo byId(Long id) {
        MemberInfoPO memberInfoPO = memberInfoMapper.selectById(id);
        return memberInfoConverter.poToDomain(memberInfoPO);
    }

    @Override
    public MemberInfo save(MemberInfo memberInfo) {
        MemberInfoPO memberInfoPO = memberInfoConverter.domainToPo(memberInfo);
        memberInfoMapper.insert(memberInfoPO);
        memberInfo.setId(memberInfoPO.getId());
        return memberInfo;
    }

    @Override
    public MemberInfo update(MemberInfo memberInfo) {
        MemberInfoPO memberInfoPO = memberInfoConverter.domainToPo(memberInfo);
        memberInfoMapper.updateById(memberInfoPO);
        return memberInfo;
    }
}
