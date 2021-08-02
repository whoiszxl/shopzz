package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.entity.response.RechargeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 比特币feign接口
 */
@FeignClient(name = "zero-bitcoin", contextId = "bitcoinFeign", configuration = OAuth2FeignConfig.class)
public interface BTCFeignClient {

    @PostMapping("/createRecharge/{orderId}/{amount}")
    ResponseResult<RechargeResponse> createRecharge(@PathVariable("orderId") String orderId,@PathVariable("amount") String amount);
}
