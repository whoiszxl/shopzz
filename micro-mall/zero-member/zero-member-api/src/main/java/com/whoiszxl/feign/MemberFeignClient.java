package com.whoiszxl.feign;

import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.MemberAddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

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
}
