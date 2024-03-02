package com.whoiszxl.feign;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.feign.FeignTokenConfig;
import com.whoiszxl.taowu.entity.response.RechargeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 以太币feign接口
 */
@FeignClient(name = "taowu-wallet-eth", contextId = "ethFeign", configuration = FeignTokenConfig.class)
public interface ETHFeignClient extends CreateAddressFeignClient {



    @Override
    @PostMapping("/createRecharge/{orderId}/{amount}")
    ResponseResult<RechargeResponse> giveAddress(
            @PathVariable("orderId") String orderId,
            @PathVariable("amount") String amount);
}
