package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * wms feign client
 *
 * @author whoiszxl
 * @date 2021/7/20
 */
@FeignClient(name = "zero-wms-web", contextId = "wmsFeign", configuration = OAuth2FeignConfig.class)
public interface WmsFeignClient {

    /**
     * 通过WMS中心，采购结算单审核已经通过了
     * @param purchaseInboundOrderId 采购入库单ID
     * @return 是否处理成功
     */
    @PostMapping("/notifyFinishedPurchaseSettlementOrderEvent")
    ResponseResult<Boolean> notifyFinishedPurchaseSettlementOrderEvent(Long purchaseInboundOrderId);

    /**
     * 通知WMS中心，创建采购结算单的事件发生了
     * @param purchaseInboundOrderId 采购入库单ID
     * @return
     */
    @PostMapping("/notifyCreatePurchaseSettlementOrderEvent/{purchaseInboundOrderId}")
    ResponseResult<Boolean> notifyCreatePurchaseSettlementOrderEvent(@PathVariable Long purchaseInboundOrderId);
}
