package com.whoiszxl.factory.handler;

import com.whoiszxl.cqrs.response.PurchaseOrderResponse;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 通知财务中心的handler
 */
@Slf4j
@Component
public class NotifyFinanceCenterHandler extends AbstractPurchaseOrderHandler {


    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseOrderResponse purchaseOrderResponse) {
        //TODO 创建一个结算单
        log.info("创建结算单成功");
        return new PurchaseInboundOrderResult(true);
    }
}
