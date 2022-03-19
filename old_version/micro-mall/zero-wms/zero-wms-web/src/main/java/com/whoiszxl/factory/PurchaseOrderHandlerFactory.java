package com.whoiszxl.factory;

import com.whoiszxl.factory.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 采购入库单的handler工厂，创建一个采购入库的责任链
 */
@Component
public class PurchaseOrderHandlerFactory {

    /**
     * 是否构建好了责任链
     */
    private Boolean isBuildedHandlerChain = false;

    /** 更新采购入库单状态为已入库的的handler */
    @Autowired
    private UpdatePurchaseOrderStatusHandler updatePurchaseOrderStatusHandler;

    /** 通知采购中心的handler */
    @Autowired
    private NotifyPurchaseCenterHandler notifyPurchaseCenterHandler;

    /** 更新库存的handler */
    @Autowired
    private UpdateStockHandler updateStockHandler;

    /** 通知库存中心的handler */
    @Autowired
    private NotifyInventoryCenterHandler notifyInventoryCenterHandler;

    /** 通知财务中心的handler */
    @Autowired
    private NotifyFinanceCenterHandler notifyFinanceCenterHandler;

    /**
     * 获取调用链
     * @return handler调用链
     */
    public PurchaseOrderHandler getHandlerChain() {
        if(!isBuildedHandlerChain) {
            buildHandlerChain();
        }
        return updatePurchaseOrderStatusHandler;
    }


    /**
     * 构建handler调用链
     * 1. 先调用更新采购单状态为已入库
     * 2. 再去更新WMS库存信息
     * 3. 再去通知库存中心去新增库存
     * 4. 再去通知财务中心去处理财务信息，财务中心会创建一个编辑中的采购结算单
     */
    private void buildHandlerChain() {
        updatePurchaseOrderStatusHandler.setSuccessor(updateStockHandler);
        updateStockHandler.setSuccessor(notifyInventoryCenterHandler);
        notifyInventoryCenterHandler.setSuccessor(notifyFinanceCenterHandler);
        this.isBuildedHandlerChain = true;
    }
}
