package com.whoiszxl.taowu.member.feign;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.feign.FeignTokenConfig;
import com.whoiszxl.taowu.member.dto.MemberAddressFeignDTO;
import com.whoiszxl.taowu.member.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 会员feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "taowu-member", contextId = "memberFeign", configuration = FeignTokenConfig.class)
public interface MemberFeignClient {

    /**
     * 通过memberId获取member信息
     * @param memberId
     * @return
     */
    @GetMapping("/member/info")
    ResponseResult<MemberDTO> getMemberInfoById(@RequestParam Long memberId);

    /**
     * 通过用户ID获取用户的详细信息
     * @param memberIdList
     * @return
     */
    @PostMapping("/member/info")
    List<MemberDTO> findMemberInfoByIds(@RequestBody List<Long> memberIdList);

    /**
     * 通过地址ID查询当前用户的地址信息
     * @param addressId 地址ID
     * @return
     */
    @GetMapping("/getMemberAddress")
    ResponseResult<MemberAddressFeignDTO> getMemberAddress(@RequestParam Long addressId);
}
