package com.whoiszxl.zero.feign;

import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.config.feign.OAuth2FeignConfig;
import com.whoiszxl.zero.vo.MemberAddressVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "zero-member-web", contextId = "memberAddressFeign", configuration = OAuth2FeignConfig.class, path = "/address")
public interface MemberAddressFeign {

    @GetMapping("/list")
    Result<List<MemberAddressVO>> list();
}
