package com.whoiszxl.factory.handler;

import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通知采购中心的handler
 */
@Component
public class NotifyPurchaseCenterHandler extends AbstractPurchaseInboundOrderHandler {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        //通知采购中心，采购入库已经完成了，需要更新采购单的状态为已入库
        purchaseOrderService.updateStatus(purchaseInboundOrderDTO.getPurchaseOrderId(), PurchaseOrderStatusConstants.FINISHED_INBOUND);
        return new PurchaseInboundOrderResult(true);
    }
}
