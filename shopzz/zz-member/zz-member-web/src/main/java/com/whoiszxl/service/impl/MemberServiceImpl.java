package com.whoiszxl.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Member;
import com.whoiszxl.entity.MemberInfo;
import com.whoiszxl.entity.vo.MemberDetailVO;
import com.whoiszxl.entity.vo.MemberInfoVO;
import com.whoiszxl.entity.vo.MemberVO;
import com.whoiszxl.mapper.MemberMapper;
import com.whoiszxl.service.MemberInfoService;
import com.whoiszxl.service.MemberService;
import com.whoiszxl.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public MemberDetailVO memberInfo() {
        //1. 获取到当前登录用户的ID
        long memberId = StpUtil.getLoginIdAsLong();

        //2. 分别从会员、会员信息表中查询出
        Member member = this.getById(memberId);
        MemberVO memberVO = dozerUtils.map(member, MemberVO.class);

        MemberInfo memberInfo = memberInfoService.getByMemberId(memberId);
        MemberInfoVO memberInfoVO = dozerUtils.map(memberInfo, MemberInfoVO.class);

        MemberDetailVO memberDetailVO = new MemberDetailVO();
        memberDetailVO.setMemberInfo(memberInfoVO);
        memberDetailVO.setMember(memberVO);
        return memberDetailVO;
    }

    @Override
    public boolean passwordRegister(String username, String password) {
        long memberId = idWorker.nextId();
        Member member = new Member();
        member.setId(memberId);
        member.setUsername(username);
        member.setPhone(username);
        member.setPassword(passwordEncoder.encode(password));
        this.save(member);

        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberId(memberId);
        memberInfoService.save(memberInfo);
        return true;
    }
}
