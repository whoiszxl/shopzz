package com.whoiszxl.factory.handler;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.feign.InventoryFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyDispatchCenterHandler extends AbstractPurchaseInboundOrderHandler {

    @Autowired
    private InventoryFeignClient inventoryFeignClient;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        //直接更新库存中心库存
        inventoryFeignClient.notifyPurchaseInboundFinished(purchaseInboundOrderDTO);
        return new PurchaseInboundOrderResult(true);
    }
}
