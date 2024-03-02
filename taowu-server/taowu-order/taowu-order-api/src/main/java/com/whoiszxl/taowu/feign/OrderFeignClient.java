package com.whoiszxl.taowu.feign;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.feign.FeignTokenConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * order feign client
 *
 * @author whoiszxl
 * @date 2021/7/20
 */
@FeignClient(name = "taowu-order-web", contextId = "orderFeign", configuration = FeignTokenConfig.class)
public interface OrderFeignClient {

    /**
     * 通知订单中心，数据货币支付成功了
     * @param orderIds 订单ID集合
     * @return
     */
    @PostMapping("/notifyDcPaySuccess")
    ResponseResult<Boolean> notifyDcPaySuccess(@RequestBody List<Long> orderIds);

}
