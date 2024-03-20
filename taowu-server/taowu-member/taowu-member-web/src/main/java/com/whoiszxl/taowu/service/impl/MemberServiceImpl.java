package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.Member;
import com.whoiszxl.taowu.member.dto.MemberDTO;
import com.whoiszxl.taowu.mapper.MemberMapper;
import com.whoiszxl.taowu.service.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    private final MemberMapper memberMapper;

    @Override
    public List<MemberDTO> findMemberInfoByIds(List<Long> memberIdList) {
        List<Member> memberPOList = memberMapper.selectList(Wrappers.<Member>lambdaQuery().in(Member::getId, memberIdList));
        List<MemberDTO> memberDTOList = BeanUtil.copyToList(memberPOList, MemberDTO.class);
        return memberDTOList;
    }

}
