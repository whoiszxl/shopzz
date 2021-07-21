package com.whoiszxl.factory.handler;

import com.whoiszxl.constant.WmsStockUpdateEvent;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.factory.PurchaseInboundOrderResult;
import com.whoiszxl.factory.handler.AbstractPurchaseInboundOrderHandler;
import com.whoiszxl.stock.WmsStockUpdater;
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
        //通过库存更新工厂创建一个采购入库库存更新组件来更新
        WmsStockUpdater wmsStockUpdater = stockUpdaterFactory.create(WmsStockUpdateEvent.PURCHASE_INBOUND, purchaseInboundOrderDTO);
        wmsStockUpdater.update();
        return new PurchaseInboundOrderResult(true);
    }
}
