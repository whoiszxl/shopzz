package com.whoiszxl.factory.handler;

import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.service.PurchaseSettlementOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通知财务中心的handler
 */
@Component
public class NotifyFinanceCenterHandler extends AbstractPurchaseOrderHandler {

    @Autowired
    private PurchaseSettlementOrderService purchaseSettlementOrderService;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseOrderDTO purchaseOrderDTO) {
        //创建一个结算单
        purchaseSettlementOrderService.createPurchaseSettlementOrder(purchaseOrderDTO);
        return new PurchaseInboundOrderResult(true);
    }
}
