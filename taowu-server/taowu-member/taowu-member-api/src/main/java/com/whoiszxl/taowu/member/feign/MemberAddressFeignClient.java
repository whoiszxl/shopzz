package com.whoiszxl.taowu.member.feign;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.feign.FeignTokenConfig;
import com.whoiszxl.taowu.member.dto.MemberAddressFeignDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 会员feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "taowu-member", contextId = "memberAddressFeign", configuration = FeignTokenConfig.class)
public interface MemberAddressFeignClient {

    /**
     * 通过地址ID查询当前用户的地址信息
     * @param addressId 地址ID
     * @return
     */
    @GetMapping("/getMemberAddress")
    ResponseResult<MemberAddressFeignDTO> getMemberAddress(@RequestParam Long addressId);
}
