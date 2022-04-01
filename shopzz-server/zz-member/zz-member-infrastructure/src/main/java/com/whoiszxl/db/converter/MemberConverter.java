package com.whoiszxl.db.converter;

import com.whoiszxl.aggregate.model.Member;
import com.whoiszxl.db.model.MemberPO;
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
public class MemberConverter {

    @Autowired
    private DozerUtils dozerUtils;


    public Member poToDomain(MemberPO memberPO) {
        Member member = dozerUtils.map(memberPO, Member.class);
        return member;
    }

    public MemberPO domainToPo(Member member) {
        MemberPO memberPO = dozerUtils.map(member, MemberPO.class);
        return memberPO;
    }
}
