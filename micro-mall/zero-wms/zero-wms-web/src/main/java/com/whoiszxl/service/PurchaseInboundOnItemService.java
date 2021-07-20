package com.whoiszxl.service;

import com.whoiszxl.entity.PurchaseInboundOnItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 采购入库订单条目关联的上架条目表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
public interface PurchaseInboundOnItemService extends IService<PurchaseInboundOnItem> {

    /**
     * 根据采购入库单条目id获取上架条目
     * @param id 采购入库单条目id
     * @return 上架条目
     */
    List<PurchaseInboundOnItem> listByPurchaseInboundOrderItemId(Long id);
}
