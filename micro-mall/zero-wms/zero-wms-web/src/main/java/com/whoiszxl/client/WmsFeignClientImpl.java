package com.whoiszxl.client;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constant.PurchaseInboundOrderStatus;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 通过WMS中心，采购结算单审核已经通过了
     * @param purchaseInboundOrderId 采购入库单ID
     * @return 是否处理成功
     */
    @Override
    @PostMapping("/notifyFinishedPurchaseSettlementOrderEvent")
    public ResponseResult<Boolean> notifyFinishedPurchaseSettlementOrderEvent(Long purchaseInboundOrderId) {
        //1. 更新采购入库单状态为已完成
        purchaseInboundOrderService.updateStatus(purchaseInboundOrderId, PurchaseInboundOrderStatus.FINISHED);

        //2. 更新采购单状态为已完成
        PurchaseInboundOrder purchaseInboundOrder = purchaseInboundOrderService.getById(purchaseInboundOrderId);
        purchaseOrderService.updateStatus(purchaseInboundOrder.getPurchaseOrderId(), PurchaseOrderStatus.FINISHED);
        return ResponseResult.buildSuccess();
    }

    @Override
    @PostMapping("/notifyCreatePurchaseSettlementOrderEvent/{purchaseInboundOrderId}")
    public ResponseResult<Boolean> notifyCreatePurchaseSettlementOrderEvent(@PathVariable Long purchaseInboundOrderId) {
        //1. 更新采购入库单的状态为待结算
        PurchaseInboundOrderDTO purchaseInboundOrderDTO = purchaseInboundOrderService.getPurchaseInboundOrderById(purchaseInboundOrderId);
        purchaseInboundOrderService.updateStatus(purchaseInboundOrderId, PurchaseInboundOrderStatus.WAIT_FOR_SETTLEMENT);

        //2. 更新采购单的状态为待结算
        purchaseOrderService.updateStatus(purchaseInboundOrderDTO.getPurchaseOrderId(), PurchaseOrderStatus.WAIT_FOR_SETTLEMENT);
        return ResponseResult.buildSuccess();
    }
}
