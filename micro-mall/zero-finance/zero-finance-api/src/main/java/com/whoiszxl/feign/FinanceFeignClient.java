package com.whoiszxl.feign;

import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 财务 feign client
 *
 * @author whoiszxl
 * @date 2021/7/20
 */
@FeignClient(name = "zero-finance-web", contextId = "financeFeign", configuration = OAuth2FeignConfig.class)
public interface FinanceFeignClient {

    /**
     * 创建采购结算单
     * @param purchaseInboundOrderDTO 采购入库单DTO
     * @return 是否创建成功
     */
    @PostMapping("/createPurchaseSettlementOrder")
    Boolean createPurchaseSettlementOrder(@RequestBody PurchaseInboundOrderDTO purchaseInboundOrderDTO);

}
