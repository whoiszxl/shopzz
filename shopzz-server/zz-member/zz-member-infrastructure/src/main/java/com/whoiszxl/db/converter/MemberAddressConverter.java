package com.whoiszxl.db.converter;

import com.whoiszxl.aggregate.model.MemberAddress;
import com.whoiszxl.db.model.MemberAddressPO;
import com.whoiszxl.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户收货地址转换器
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Component
public class MemberAddressConverter {

    @Autowired
    private DozerUtils dozerUtils;


    public MemberAddress poToDomain(MemberAddressPO memberAddressPO) {
        MemberAddress memberAddress = dozerUtils.map(memberAddressPO, MemberAddress.class);
        return memberAddress;
    }

    public MemberAddressPO domainToPo(MemberAddress memberAddress) {
        MemberAddressPO memberAddressPO = dozerUtils.map(memberAddress, MemberAddressPO.class);
        return memberAddressPO;
    }
}
