package com.whoiszxl.client;

import com.whoiszxl.constant.PurchaseOrderStatus;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.entity.PurchaseInboundOrder;
import com.whoiszxl.entity.PurchaseInboundOrderItem;
import com.whoiszxl.feign.WmsFeignClient;
import com.whoiszxl.service.PurchaseInboundOrderItemService;
import com.whoiszxl.service.PurchaseInboundOrderService;
import com.whoiszxl.service.PurchaseOrderService;
import com.whoiszxl.utils.BeanCopierUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * wms feign client
 *
 * @author whoiszxl
 * @date 2021/7/20
 */
@Slf4j
@RestController
public class WmsFeignClientImpl implements WmsFeignClient {

    @Autowired
    private PurchaseInboundOrderService purchaseInboundOrderService;

    @Autowired
    private PurchaseInboundOrderItemService purchaseInboundOrderItemService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    @Override
    public Boolean createPurchaseInboundOrder(PurchaseInboundOrderDTO purchaseInboundOrderDTO) {
        //1. 将入库单保存到数据库
        PurchaseInboundOrder purchaseInboundOrder = purchaseInboundOrderDTO.clone(PurchaseInboundOrder.class);
        purchaseInboundOrderService.save(purchaseInboundOrder);

        //2. 将入库单商品条目批量保存到数据库
        List<PurchaseInboundOrderItem> purchaseInboundOrderItems = BeanCopierUtils.copyListProperties(purchaseInboundOrderDTO.getItems(), PurchaseInboundOrderItem::new);
        purchaseInboundOrderItems.forEach(item -> item.setId(purchaseInboundOrder.getId()));
        purchaseInboundOrderItemService.saveBatch(purchaseInboundOrderItems);

        //3. 更新采购单的状态为待入库
        purchaseOrderService.updateStatus(purchaseInboundOrderDTO.getPurchaseOrderId(), PurchaseOrderStatus.WAIT_FOR_INBOUND);
        return true;
    }
}
