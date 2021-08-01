package com.whoiszxl.feign;

import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.MemberAddressDTO;
import com.whoiszxl.dto.MemberDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 会员feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "zero-member-web", contextId = "memberFeign", configuration = OAuth2FeignConfig.class)
public interface MemberFeignClient {

    /**
     * 获取会员的收货地址
     * @return 收货地址列表
     */
    @GetMapping("/listMemberAddress")
    List<MemberAddressDTO> listMemberAddress();

    /**
     * 获取当前会员的信息
     * @return
     */
    @GetMapping("/getMemberInfo")
    MemberDetailDTO getMemberInfo();

    /**
     * 通过地址ID查询当前用户的地址信息
     * @param addressId
     * @return
     */
    @GetMapping("/getMemberAddress/{addressId}")
    MemberAddressDTO getMemberAddress(@PathVariable String addressId);
}
