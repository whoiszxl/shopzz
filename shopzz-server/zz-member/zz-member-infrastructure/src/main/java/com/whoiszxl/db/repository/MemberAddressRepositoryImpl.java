package com.whoiszxl.db.repository;

import com.whoiszxl.aggregate.model.MemberAddress;
import com.whoiszxl.aggregate.repository.MemberAddressRepository;
import com.whoiszxl.db.converter.MemberAddressConverter;
import com.whoiszxl.db.mapper.MemberAddressMapper;
import com.whoiszxl.db.mapper.MemberInfoMapper;
import com.whoiszxl.db.model.MemberAddressPO;
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
public class MemberAddressRepositoryImpl implements MemberAddressRepository {

    @Autowired
    private MemberAddressMapper memberAddressMapper;

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private MemberAddressConverter memberAddressConverter;


    @Override
    public void delete(Long id) {
        memberAddressMapper.deleteById(id);
    }

    @Override
    public MemberAddress byId(Long id) {
        MemberAddressPO memberPO = memberAddressMapper.selectById(id);
        if(Objects.isNull(memberPO)) {
            return null;
        }
        return memberAddressConverter.poToDomain(memberPO);
    }

    @Override
    public MemberAddress save(MemberAddress memberAddress) {
        MemberAddressPO memberAddressPO = memberAddressConverter.domainToPo(memberAddress);
        memberAddressMapper.insert(memberAddressPO);
        memberAddress.setId(memberAddressPO.getId());
        return memberAddress;
    }


    @Override
    public MemberAddress update(MemberAddress memberAddress) {
        MemberAddressPO memberAddressPO = memberAddressConverter.domainToPo(memberAddress);
        memberAddressMapper.updateById(memberAddressPO);
        return memberAddress;
    }
}
