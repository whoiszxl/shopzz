package com.whoiszxl.factory.handler;

import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新采购入库单状态为 已入库 的handler
 */
@Component
public class UpdatePurchaseOrderStatusHandler extends AbstractPurchaseOrderHandler {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseOrderDTO purchaseOrderDTO) {
        //将状态更新为已入库
        purchaseOrderService.updateStatus(purchaseOrderDTO.getId(), PurchaseOrderStatusConstants.FINISHED_INBOUND);
        return new PurchaseInboundOrderResult(true);
    }
}
