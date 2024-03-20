package com.whoiszxl.taowu.feign;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.feign.FeignTokenConfig;
import com.whoiszxl.taowu.dto.CountFeignCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 计数feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "taowu-counter", contextId = "counterFeign", configuration = FeignTokenConfig.class)
public interface CounterFeignClient {


    @PostMapping("/countNumber")
    ResponseResult<Boolean> countNumber(@RequestBody CountFeignCommand command);

}
