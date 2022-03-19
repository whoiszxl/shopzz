package com.whoiszxl.factory.handler;

import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.cqrs.response.PurchaseOrderResponse;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通知采购中心的handler
 */
@Slf4j
@Component
public class NotifyPurchaseCenterHandler extends AbstractPurchaseOrderHandler {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseOrderResponse purchaseOrderResponse) {
        log.info("通知采购中心，采购入库已经完成了，需要更新采购单的状态为已入库");
        purchaseOrderService.updateStatus(purchaseOrderResponse.getId(), PurchaseOrderStatusConstants.FINISHED_INBOUND);
        return new PurchaseInboundOrderResult(true);
    }
}
