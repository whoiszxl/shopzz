package com.whoiszxl.taowu.feign;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.feign.FeignTokenConfig;
import com.whoiszxl.taowu.dto.VideoAuditMqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 会员feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "taowu-sensitive", contextId = "SensitiveWordFeign", configuration = FeignTokenConfig.class)
public interface SensitiveWordFeignClient {


    @PostMapping("/verify/keyword")
    ResponseResult<Boolean> verifyKeyword(@RequestBody VideoAuditMqDto videoAuditMqDto);
}
