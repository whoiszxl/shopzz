package com.whoiszxl.client;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.dto.PurchaseOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 调度中心feign client
 *
 * @author whoiszxl
 * @date 2021/7/20
 */
@FeignClient(name = "zero-dispatch-web", contextId = "dispatchFeign", configuration = OAuth2FeignConfig.class)
public interface DispatchClient {

    /**
     * 调度采购入库
     * @param purchaseOrderDTO 采购订单DTO
     * @return 是否调度成功
     */
    @PostMapping("/dispatchPurchaseInBound")
    ResponseResult<Boolean> dispatchPurchaseInBound(@RequestBody PurchaseOrderDTO purchaseOrderDTO);

    /**
     * 通知库存中心，“采购入库完成”事件发生了
     * @param purchaseInboundOrderDTO 采购入库单DTO
     * @return 是否处理成功
     */
    @PostMapping("/notifyPurchaseInboundFinished")
    ResponseResult<Boolean> notifyPurchaseInboundFinished(PurchaseInboundOrderDTO purchaseInboundOrderDTO);
}
