package com.whoiszxl.stock;

import com.whoiszxl.constants.WmsStockUpdateEventConstants;
import com.whoiszxl.utils.SpringApplicationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 调度中心库存更新工厂
 */
@Component
public class DispatchStockUpdaterFactory {

    @Autowired
    private SpringApplicationContextUtil contextUtil;

    /**
     * 创建一个库存更新命令
     * @param stockUpdateEvent 库存更新类型
     * @param parameter 参数
     * @return 库存更新命令
     */
    public DispatchStockUpdater create(Integer stockUpdateEvent, Object parameter) {
        DispatchStockUpdater stockUpdater = null;

        if(WmsStockUpdateEventConstants.SUBMIT_ORDER.equals(stockUpdateEvent)) {

        } else if(WmsStockUpdateEventConstants.CANCEL_ORDER.equals(stockUpdateEvent)) {

        } else if(WmsStockUpdateEventConstants.PAY_ORDER.equals(stockUpdateEvent)) {

        } else if(WmsStockUpdateEventConstants.PURCHASE_INBOUND.equals(stockUpdateEvent)) {
            stockUpdater = contextUtil.getBean(PurchaseInboundDispatchStockUpdater.class);
        } else if(WmsStockUpdateEventConstants.RETURN_PRODUCT_INBOUND.equals(stockUpdateEvent)) {

        }

        if(stockUpdater != null) {
            stockUpdater.setParameter(parameter);
        }
        return stockUpdater;
    }


}
