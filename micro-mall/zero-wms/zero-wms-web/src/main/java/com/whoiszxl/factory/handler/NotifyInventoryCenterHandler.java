package com.whoiszxl.factory.handler;

import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.feign.InventoryFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyInventoryCenterHandler extends AbstractPurchaseOrderHandler {

    @Autowired
    private InventoryFeignClient inventoryFeignClient;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseOrderDTO purchaseOrderDTO) {
        //直接更新库存中心库存
        inventoryFeignClient.notifyPurchaseOrderFinished(purchaseOrderDTO);
        return new PurchaseInboundOrderResult(true);
    }
}
