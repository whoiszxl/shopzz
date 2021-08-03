package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.entity.response.RechargeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * zxl erc20代币feign接口
 */
@FeignClient(name = "zero-zxl", contextId = "zxlFeign", configuration = OAuth2FeignConfig.class)
public interface ZXLFeignClient extends CreateAddressFeignClient {

    @Override
    @PostMapping("/createRecharge/{orderId}/{amount}")
    ResponseResult<RechargeResponse> giveAddress(
            @PathVariable("orderId") String orderId,
            @PathVariable("amount") String amount);
}
