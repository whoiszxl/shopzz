package com.whoiszxl.factory;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import org.springframework.stereotype.Component;

/**
 * 通知财务中心的handler
 */
@Component
public class NotifyFinanceCenterHandler extends AbstractPurchaseInboundOrderHandler {

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        //TODO
        return new PurchaseInboundOrderResult(true);
    }
}
