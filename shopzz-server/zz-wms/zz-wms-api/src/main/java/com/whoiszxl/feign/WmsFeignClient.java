package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.OrderInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * wms feign client
 *
 * @author whoiszxl
 * @date 2021/7/20
 */
@FeignClient(name = "zz-wms-web", contextId = "wmsFeign", configuration = OAuth2FeignConfig.class)
public interface WmsFeignClient {

    /**
     * 通知WMS中心，支付订单的事件发生了
     * @param orderInfo 订单信息
     * @return
     */
    @PostMapping("/notifyPayOrderEvent")
    ResponseResult<Boolean> notifyPayOrderSuccess(@RequestBody OrderInfoDTO orderInfo);

}
