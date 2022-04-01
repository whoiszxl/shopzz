package com.whoiszxl.db.converter;

import com.whoiszxl.aggregate.model.MemberInfo;
import com.whoiszxl.db.model.MemberInfoPO;
import com.whoiszxl.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户转换器
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Component
public class MemberInfoConverter {

    @Autowired
    private DozerUtils dozerUtils;

    public MemberInfo poToDomain(MemberInfoPO memberInfoPO) {
        MemberInfo memberInfo = dozerUtils.map(memberInfoPO, MemberInfo.class);
        return memberInfo;
    }

    public MemberInfoPO domainToPo(MemberInfo memberInfo) {
        MemberInfoPO memberInfoPO = dozerUtils.map(memberInfo, MemberInfoPO.class);
        return memberInfoPO;
    }
}
