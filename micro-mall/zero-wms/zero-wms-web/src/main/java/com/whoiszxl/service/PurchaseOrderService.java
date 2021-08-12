package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.entity.PurchaseOrder;
import com.whoiszxl.entity.vo.PurchaseOrderVO;

/**
 * <p>
 * 采购订单表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-19
 */
public interface PurchaseOrderService extends IService<PurchaseOrder> {

    /**
     * 新增一个采购订单
     * @param purchaseOrderVO 提交过来的采购订单信息
     * @return 是否新增成功
     */
    boolean savePurchaseOrder(PurchaseOrderVO purchaseOrderVO);

    /**
     * 通过主键ID查询一个采购订单
     * @param id 采购订单ID
     * @return 采购订单信息
     */
    PurchaseOrderDTO getPurchaseOrderById(Long id);

    /**
     * 更新一个采购订单
     * @param purchaseOrderVO 采购订单信息
     * @return 是否更新成功
     */
    Boolean updatePurchaseOrder(PurchaseOrderVO purchaseOrderVO);

    /**
     * 更新采购订单状态
     * @param id 采购订单ID
     * @param status 采购订单状态
     * @return
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 更新采购单的状态为已完成，通过采购商ID
     * @param id 采购商ID
     */
    boolean updateFinishedBySupplierId(Long id);
}
