package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.MemberDao;
import com.whoiszxl.zero.dao.MemberInfoDao;
import com.whoiszxl.zero.entity.Member;
import com.whoiszxl.zero.entity.MemberInfo;
import com.whoiszxl.zero.entity.vo.MemberDetailVO;
import com.whoiszxl.zero.entity.vo.MemberInfoVO;
import com.whoiszxl.zero.entity.vo.MemberVO;
import com.whoiszxl.zero.service.MemberService;
import com.whoiszxl.zero.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberInfoDao memberInfoDao;

    @Override
    public MemberDetailVO memberInfo() {
        //从jwt中获取用户ID
        Long memberId = Long.parseLong(JwtUtils.getUsername());
        Member member = memberDao.findById(memberId);
        MemberInfo memberInfo = memberInfoDao.findByMemberId(memberId);
        MemberVO memberVO = member.clone(MemberVO.class);
        MemberInfoVO memberInfoVO = memberInfo.clone(MemberInfoVO.class);

        MemberDetailVO memberDetailVO = new MemberDetailVO();
        memberDetailVO.setMemberVO(memberVO);
        memberDetailVO.setMemberInfoVO(memberInfoVO);

        return memberDetailVO;
    }
}
