package com.whoiszxl.stock;

import com.whoiszxl.constants.WmsStockUpdateEventConstants;
import com.whoiszxl.utils.SpringApplicationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WmsStockUpdaterFactory {

    @Autowired
    private SpringApplicationContextUtil springApplicationContextUtil;

    public WmsStockUpdater create(Integer stockUpdateEvent, Object parameter) {
        WmsStockUpdater wmsStockUpdater = null;
        if(WmsStockUpdateEventConstants.PURCHASE_INBOUND.equals(stockUpdateEvent)) {
            wmsStockUpdater = springApplicationContextUtil.getBean(PurchaseInboundWmsStockUpdater.class);
        }

        else if(WmsStockUpdateEventConstants.RETURN_PRODUCT_INBOUND.equals(stockUpdateEvent)){
            //TODO 退货
        }

        else if(WmsStockUpdateEventConstants.SUBMIT_ORDER.equals(stockUpdateEvent)) {
            wmsStockUpdater = springApplicationContextUtil.getBean(SubmitOrderWmsStockUpdater.class);
        }

        else if(WmsStockUpdateEventConstants.CANCEL_ORDER.equals(stockUpdateEvent)) {
            //TODO 取消订单
        }

        else if(WmsStockUpdateEventConstants.PAY_ORDER.equals(stockUpdateEvent)) {
            wmsStockUpdater = springApplicationContextUtil.getBean(PayOrderWmsStockUpdater.class);
        }

        wmsStockUpdater.setParameter(parameter);
        return wmsStockUpdater;
    }

}
