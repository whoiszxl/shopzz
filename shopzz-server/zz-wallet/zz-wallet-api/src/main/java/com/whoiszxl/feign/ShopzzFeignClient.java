package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.entity.response.RechargeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * shopzz erc20代币feign接口
 */
@FeignClient(name = "zz-wallet-shopzz", contextId = "shopzzFeign", configuration = OAuth2FeignConfig.class)
public interface ShopzzFeignClient extends CreateAddressFeignClient {

    @Override
    @PostMapping("/createRecharge/{orderId}/{amount}")
    ResponseResult<RechargeResponse> giveAddress(
            @PathVariable("orderId") String orderId,
            @PathVariable("amount") String amount);
}
