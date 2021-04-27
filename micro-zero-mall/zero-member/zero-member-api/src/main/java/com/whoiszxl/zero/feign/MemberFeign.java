package com.whoiszxl.zero.feign;

import com.whoiszxl.zero.config.feign.OAuth2FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "zero-member-web", contextId = "memberFeign", configuration = OAuth2FeignConfig.class, path = "/")
public interface MemberFeign {

}
