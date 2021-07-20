package com.whoiszxl.factory;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;

/**
 * 采购入库订单处理的handler接口
 */
public interface PurchaseInboundOrderHandler {

    /**
     * 执行处理逻辑
     * @param purchaseInboundOrderDTO 采购入库单
     * @return 处理是否成功
     */
    Boolean execute(PurchaseInboundOrderDTO purchaseInboundOrderDTO);
}
