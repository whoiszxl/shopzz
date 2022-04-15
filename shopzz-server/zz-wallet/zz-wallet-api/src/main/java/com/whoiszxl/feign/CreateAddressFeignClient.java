package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.response.RechargeResponse;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 地址创建feign接口
 */
public interface CreateAddressFeignClient {

    /**
     * 分配地址Feign接口，其他币种对此接口方法进行实现
     * @param orderId 订单ID
     * @param amount 金额
     * @return
     */
    ResponseResult<RechargeResponse> giveAddress(
            @PathVariable("orderId") String orderId,
            @PathVariable("amount") String amount);
}
