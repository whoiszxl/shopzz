package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.cqrs.command.PurchaseOrderSaveCommand;
import com.whoiszxl.cqrs.response.PurchaseOrderResponse;
import com.whoiszxl.entity.PurchaseOrder;

/**
 * TODO
 *
 * @author zhouxiaolong
 * @date 2022/3/18
 */
public interface PurchaseOrderService extends IService<PurchaseOrder> {

    /**
     * 保存采购订单
     * @param purchaseOrderSaveCommand
     */
    void savePurchaseOrder(PurchaseOrderSaveCommand purchaseOrderSaveCommand);

    /**
     * 通过采购单ID获取到采购单的详情
     * @param id
     * @return
     */
    PurchaseOrderResponse getPurchaseOrderById(Long id);

    /**
     * 更新采购订单
     * @param purchaseOrderSaveCommand
     */
    void updatePurchaseOrder(PurchaseOrderSaveCommand purchaseOrderSaveCommand);

    /**
     * 更新采购订单状态
     * @param id 采购订单ID
     * @param status 采购订单状态
     * @return
     */
    boolean updateStatus(Long id, Integer code);

    /**
     * 更新采购单的状态为已完成，通过采购商ID
     * @param id 采购商ID
     */
    boolean updateFinishedBySupplierId(Long id);
}
