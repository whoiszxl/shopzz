package com.whoiszxl.factory.handler;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.service.PurchaseSettlementOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通知财务中心的handler
 */
@Component
public class NotifyFinanceCenterHandler extends AbstractPurchaseInboundOrderHandler {

    @Autowired
    private PurchaseSettlementOrderService purchaseSettlementOrderService;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        //创建一个结算单
        purchaseSettlementOrderService.createPurchaseSettlementOrder(purchaseInboundOrderDTO);
        return new PurchaseInboundOrderResult(true);
    }
}
