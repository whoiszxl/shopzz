package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * wms feign client
 *
 * @author whoiszxl
 * @date 2021/7/20
 */
@FeignClient(name = "zero-wms-web", contextId = "wmsFeign", configuration = OAuth2FeignConfig.class)
public interface WmsFeignClient {


    /**
     * 创建采购入库订单
     * @param purchaseInboundOrderDTO 采购入库订单DTO
     * @return 处理结果
     */
    @PostMapping("/createPurchaseInboundOrder")
    ResponseResult<Boolean> createPurchaseInboundOrder(@RequestBody PurchaseInboundOrderDTO purchaseInboundOrderDTO);


    /**
     * 通过WMS中心，采购结算单审核已经通过了
     * @param purchaseInboundOrderId 采购入库单ID
     * @return 是否处理成功
     */
    @PostMapping("/notifyFinishedPurchaseSettlementOrderEvent")
    ResponseResult<Boolean> notifyFinishedPurchaseSettlementOrderEvent(Long purchaseInboundOrderId);

}
