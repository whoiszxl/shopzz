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

    /** 更新采购入库单状态为已入库的的handler */
    @Autowired
    private UpdatePurchaseInboundOrderStatusHandler updatePurchaseInboundOrderStatusHandler;

    /** 通知采购中心的handler */
    @Autowired
    private NotifyPurchaseCenterHandler notifyPurchaseCenterHandler;

    /** 更新库存的handler */
    @Autowired
    private UpdateStockHandler updateStockHandler;

    /** 通知调度中心的handler */
    @Autowired
    private NotifyDispatchCenterHandler notifyDispatchCenterHandler;

    /** 通知财务中心的handler */
    @Autowired
    private NotifyFinanceCenterHandler notifyFinanceCenterHandler;

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
     * 1. 先调用更新采购入库单状态为已入库
     * 2. 再去更新库存信息
     * 3. 再去通知调度中心去处理一些信息
     * 4. 再去通知财务中心去处理财务信息
     */
    private void buildHandlerChain() {
        updatePurchaseInboundOrderStatusHandler.setSuccessor(notifyPurchaseCenterHandler);
        notifyPurchaseCenterHandler.setSuccessor(updateStockHandler);
        updateStockHandler.setSuccessor(notifyDispatchCenterHandler);
        notifyDispatchCenterHandler.setSuccessor(notifyFinanceCenterHandler);
    }
}
