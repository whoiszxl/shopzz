package com.whoiszxl.service;

import com.whoiszxl.entity.PurchaseInboundOrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 采购入库订单条目表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
public interface PurchaseInboundOrderItemService extends IService<PurchaseInboundOrderItem> {

    /**
     * 通过入库单的ID查询入库单条目集合
     * @param id 入库单ID
     * @return 条目集合
     */
    List<PurchaseInboundOrderItem> listByPurchaseInboundOrderId(Long id);
}
