package com.whoiszxl.stock;

import com.whoiszxl.constant.WmsStockUpdateEvent;
import com.whoiszxl.utils.SpringApplicationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WmsStockUpdaterFactory {

    @Autowired
    private SpringApplicationContextUtil springApplicationContextUtil;

    public WmsStockUpdater create(Integer stockUpdateEvent, Object parameter) {
        WmsStockUpdater wmsStockUpdater = null;
        if(WmsStockUpdateEvent.PURCHASE_INBOUND.equals(stockUpdateEvent)) {
            wmsStockUpdater = springApplicationContextUtil.getBean(PurchaseInboundWmsStockUpdater.class);
        }

        else if(WmsStockUpdateEvent.RETURN_PRODUCT_INBOUND.equals(stockUpdateEvent)){
            //TODO
        }

        else if(WmsStockUpdateEvent.SUBMIT_ORDER.equals(stockUpdateEvent)) {
            //TODO
        }

        else if(WmsStockUpdateEvent.CANCEL_ORDER.equals(stockUpdateEvent)) {
            //TODO
        }

        else if(WmsStockUpdateEvent.PAY_ORDER.equals(stockUpdateEvent)) {
            //TODO
        }

        wmsStockUpdater.setParameter(parameter);
        return wmsStockUpdater;
    }

}
