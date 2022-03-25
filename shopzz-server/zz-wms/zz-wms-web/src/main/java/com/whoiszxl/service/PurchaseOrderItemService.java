package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.cqrs.command.PurchaseOrderSaveCommand;
import com.whoiszxl.cqrs.response.PurchaseOrderResponse;
import com.whoiszxl.entity.PurchaseOrder;
import com.whoiszxl.entity.PurchaseOrderItem;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
public interface PurchaseOrderItemService extends IService<PurchaseOrderItem> {

}
