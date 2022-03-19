package com.whoiszxl.factory.handler;

import com.whoiszxl.cqrs.response.PurchaseOrderResponse;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotifyInventoryCenterHandler extends AbstractPurchaseOrderHandler {


    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseOrderResponse purchaseOrderResponse) {
        //TODO 直接更新库存中心库存
        log.info("更新库存中心库存成功");
        return new PurchaseInboundOrderResult(true);
    }
}
