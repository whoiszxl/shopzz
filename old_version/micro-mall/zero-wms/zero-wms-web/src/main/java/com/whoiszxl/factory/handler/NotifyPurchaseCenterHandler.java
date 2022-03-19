package com.whoiszxl.factory.handler;

import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通知采购中心的handler
 */
@Component
public class NotifyPurchaseCenterHandler extends AbstractPurchaseOrderHandler {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseOrderDTO purchaseOrderDTO) {
        //通知采购中心，采购入库已经完成了，需要更新采购单的状态为已入库
        purchaseOrderService.updateStatus(purchaseOrderDTO.getId(), PurchaseOrderStatusConstants.FINISHED_INBOUND);
        return new PurchaseInboundOrderResult(true);
    }
}
