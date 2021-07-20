package com.whoiszxl.feign;

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
@FeignClient(name = "zero-wms-web", contextId = "wmsFeign", configuration = OAuth2FeignConfig.class, path = "/wms")
public interface WmsFeignClient {


    /**
     * 创建采购入库订单
     * @param purchaseInboundOrderDTO 采购入库订单DTO
     * @return 处理结果
     */
    @PostMapping("/createPurchaseInboundOrder")
    Boolean createPurchaseInboundOrder(@RequestBody PurchaseInboundOrderDTO purchaseInboundOrderDTO);
}
