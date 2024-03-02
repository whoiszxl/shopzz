package com.whoiszxl.zhipin.member.feign;

import cn.hutool.core.bean.BeanUtil;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.member.dto.MemberDTO;
import com.whoiszxl.taowu.member.feign.MemberFeignClient;
import com.whoiszxl.zhipin.member.entity.Member;
import com.whoiszxl.zhipin.member.service.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberFeignClientImpl implements MemberFeignClient {

    private final IMemberService memberService;

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
}
