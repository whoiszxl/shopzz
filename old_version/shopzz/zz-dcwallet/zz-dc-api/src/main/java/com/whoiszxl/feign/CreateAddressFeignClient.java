package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.response.RechargeResponse;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 地址创建feign接口
 */
public interface CreateAddressFeignClient {

    ResponseResult<RechargeResponse> giveAddress(
            @PathVariable("orderId") String orderId,
            @PathVariable("amount") String amount);
}
