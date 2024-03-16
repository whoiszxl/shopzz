package com.whoiszxl.zhipin.member.feign;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.member.dto.MemberDTO;
import com.whoiszxl.taowu.member.feign.MemberFeignClient;
import com.whoiszxl.taowu.member.feign.MemberRelationFeignClient;
import com.whoiszxl.zhipin.member.entity.Member;
import com.whoiszxl.zhipin.member.entity.MemberFollower;
import com.whoiszxl.zhipin.member.mapper.MemberFollowerMapper;
import com.whoiszxl.zhipin.member.service.IMemberRelationService;
import com.whoiszxl.zhipin.member.service.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberFeignClientImpl implements MemberFeignClient, MemberRelationFeignClient {

    private final IMemberService memberService;

    private final MemberFollowerMapper memberFollowerMapper;


    @Override
    public ResponseResult<MemberDTO> getMemberInfoById(Long memberId) {
        Member member = memberService.getById(memberId);
        if(member == null) {
            return ResponseResult.buildError();
        }
        return ResponseResult.buildSuccess(BeanUtil.copyProperties(member, MemberDTO.class));
    }

    @Override
    public List<MemberDTO> findMemberInfoByIds(List<Long> memberIdList) {
        return memberService.findMemberInfoByIds(memberIdList);
    }

    @Override
    public ResponseResult<List<String>> getMemberFollowerIdList(Long memberId) {
        List<MemberFollower> memberFollowers = memberFollowerMapper
                .selectList(Wrappers.<MemberFollower>lambdaQuery().eq(MemberFollower::getMemberId, memberId));

        List<String> followerIdList = memberFollowers.stream()
                .map(memberFollower -> String.valueOf(memberFollower.getFollowerId())).collect(Collectors.toList());

        return ResponseResult.buildSuccess(followerIdList);
    }
}
