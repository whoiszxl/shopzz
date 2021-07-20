package com.whoiszxl.factory;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.stock.WmsStockUpdaterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新本地库存handler
 */
@Component
public class UpdateStockHandler extends AbstractPurchaseInboundOrderHandler {

    @Autowired
    private WmsStockUpdaterFactory stockUpdaterFactory;

    @Override
    protected PurchaseInboundOrderResult doExecute(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        return null;
    }
}
