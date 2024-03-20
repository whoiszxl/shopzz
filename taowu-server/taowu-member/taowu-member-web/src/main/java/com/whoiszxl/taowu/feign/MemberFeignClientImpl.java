package com.whoiszxl.taowu.feign;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.entity.Member;
import com.whoiszxl.taowu.entity.MemberAttention;
import com.whoiszxl.taowu.entity.MemberFollower;
import com.whoiszxl.taowu.mapper.MemberAttentionMapper;
import com.whoiszxl.taowu.member.dto.MemberAddressFeignDTO;
import com.whoiszxl.taowu.member.dto.MemberDTO;
import com.whoiszxl.taowu.member.feign.MemberFeignClient;
import com.whoiszxl.taowu.member.feign.MemberRelationFeignClient;
import com.whoiszxl.taowu.mapper.MemberFollowerMapper;
import com.whoiszxl.taowu.service.IMemberService;
import com.whoiszxl.taowu.service.MemberAddressApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberFeignClientImpl implements MemberFeignClient, MemberRelationFeignClient {

    private final IMemberService memberService;

    private final MemberFollowerMapper memberFollowerMapper;

    private final MemberAttentionMapper memberAttentionMapper;

    private final TokenHelper tokenHelper;

    private final MemberAddressApplicationService memberAddressApplicationService;

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


    @Override
    public ResponseResult<List<String>> getMemberAttentionIdList(Long memberId) {
        List<MemberAttention> memberAttentions = memberAttentionMapper
                .selectList(Wrappers.<MemberAttention>lambdaQuery().eq(MemberAttention::getMemberId, memberId));
        List<String> attentionIdList = memberAttentions.stream()
                .map(memberAttention -> String.valueOf(memberAttention.getAttentionId())).collect(Collectors.toList());
        return ResponseResult.buildSuccess(attentionIdList);
    }

    @Override
    public ResponseResult<MemberAddressFeignDTO> getMemberAddress(Long addressId) {
        Long memberId = tokenHelper.getAppMemberId();
        MemberAddressFeignDTO memberAddressFeignDTO = memberAddressApplicationService.getMemberAddress(memberId, addressId);
        return ResponseResult.buildSuccess(memberAddressFeignDTO);
    }

}
