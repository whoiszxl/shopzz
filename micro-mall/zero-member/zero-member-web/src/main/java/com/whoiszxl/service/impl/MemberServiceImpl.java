package com.whoiszxl.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.whoiszxl.entity.Member;
import com.whoiszxl.entity.MemberInfo;
import com.whoiszxl.entity.vo.MemberDetailVO;
import com.whoiszxl.entity.vo.MemberInfoVO;
import com.whoiszxl.entity.vo.MemberVO;
import com.whoiszxl.mapper.MemberMapper;
import com.whoiszxl.service.MemberInfoService;
import com.whoiszxl.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-29
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberInfoService memberInfoService;

    @Override
    public MemberDetailVO memberInfo() {
        //1. 获取到当前登录用户的ID
        long memberId = StpUtil.getLoginIdAsLong();

        //2. 分别从会员、会员信息表中查询出
        Member member = this.getById(memberId);
        MemberVO memberVO = member.clone(MemberVO.class);

        MemberInfo memberInfo = memberInfoService.getByMemberId(memberId);
        MemberInfoVO memberInfoVO = memberInfo.clone(MemberInfoVO.class);

        MemberDetailVO memberDetailVO = new MemberDetailVO();
        memberDetailVO.setMemberInfo(memberInfoVO);
        memberDetailVO.setMember(memberVO);
        return memberDetailVO;
    }
}
