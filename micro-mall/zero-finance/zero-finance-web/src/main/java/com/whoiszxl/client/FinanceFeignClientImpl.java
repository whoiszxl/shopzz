package com.whoiszxl.client;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.feign.FinanceFeignClient;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
@RestController
public class FinanceFeignClientImpl implements FinanceFeignClient {

    @Override
    public Boolean createPurchaseSettlementOrder(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        return null;
    }
}
