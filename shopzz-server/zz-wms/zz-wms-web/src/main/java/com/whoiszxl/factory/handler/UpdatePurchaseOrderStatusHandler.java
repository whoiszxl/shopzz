package com.whoiszxl.factory.handler;

import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.cqrs.response.PurchaseOrderResponse;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新采购入库单状态为 已入库 的handler
 */
@Slf4j
@Component
public class UpdatePurchaseOrderStatusHandler extends AbstractPurchaseOrderHandler {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseOrderResponse purchaseOrderResponse) {
        log.info("将状态更新为已入库");
        purchaseOrderService.updateStatus(purchaseOrderResponse.getId(), PurchaseOrderStatusConstants.FINISHED_INBOUND);
        return new PurchaseInboundOrderResult(true);
    }
}
