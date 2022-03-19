package com.whoiszxl.factory.handler;

import com.whoiszxl.dto.PurchaseOrderDTO;

/**
 * 采购单处理的handler接口
 */
public interface PurchaseOrderHandler {

    /**
     * 执行处理逻辑
     * @param purchaseOrderDTO 采购单
     * @return 处理是否成功
     */
    Boolean execute(PurchaseOrderDTO purchaseOrderDTO);
}
