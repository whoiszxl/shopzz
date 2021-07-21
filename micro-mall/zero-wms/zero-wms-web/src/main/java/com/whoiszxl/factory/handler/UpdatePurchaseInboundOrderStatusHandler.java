package com.whoiszxl.factory.handler;

import com.whoiszxl.constant.PurchaseInboundOrderStatus;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.service.PurchaseInboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新采购入库单状态为 已入库 的handler
 */
@Component
public class UpdatePurchaseInboundOrderStatusHandler extends AbstractPurchaseInboundOrderHandler {

    @Autowired
    private PurchaseInboundOrderService purchaseInboundOrderService;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        //将状态更新为已入库
        purchaseInboundOrderService.updateStatus(purchaseInboundOrderDTO.getId(), PurchaseInboundOrderStatus.FINISH_INBOUND);
        return new PurchaseInboundOrderResult(true);
    }
}
