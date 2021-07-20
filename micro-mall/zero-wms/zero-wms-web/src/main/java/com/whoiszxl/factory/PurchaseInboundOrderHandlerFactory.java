package com.whoiszxl.factory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 采购入库单的handler工厂，创建一个采购入库的责任链
 */
public class PurchaseInboundOrderHandlerFactory {

    /**
     * 是否构建好了责任链
     */
    private Boolean isBuildedHandlerChain = false;

    /**
     * 更新采购入库单状态为已入库的的handler
     */
    @Autowired
    private UpdatePurchaseInboundOrderStatusHandler updatePurchaseInboundOrderStatusHandler;

    @Autowired
    private NotifyPurchaseCenterHandler notifyPurchaseCenterHandler;

    @Autowired
    private UpdateStockHandler updateStockHandler;

    @Autowired
    private NotifyDispatchCenterHandler notifyDispatchCenterHandler;

    /**
     * 获取调用链
     * @return handler调用链
     */
    public PurchaseInboundOrderHandler getHandlerChain() {
        if(!isBuildedHandlerChain) {
            buildHandlerChain();
        }
        return updatePurchaseInboundOrderStatusHandler;
    }


    /**
     * 构建handler调用链
     */
    private void buildHandlerChain() {
        updatePurchaseInboundOrderStatusHandler.setSuccessor(notifyPurchaseCenterHandler);
        notifyPurchaseCenterHandler.setSuccessor(updateStockHandler);
        updateStockHandler.setSuccessor();
    }
}
