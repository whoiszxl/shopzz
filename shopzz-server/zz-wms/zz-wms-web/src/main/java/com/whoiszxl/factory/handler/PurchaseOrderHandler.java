package com.whoiszxl.factory.handler;

import com.whoiszxl.cqrs.response.PurchaseOrderResponse;

/**
 * 采购单处理的handler接口
 */
public interface PurchaseOrderHandler {

    /**
     * 执行处理逻辑
     * @param purchaseOrderResponse 采购单信息
     * @return 处理是否成功
     */
    Boolean execute(PurchaseOrderResponse purchaseOrderResponse);
}
