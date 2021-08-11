package com.whoiszxl.client;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.PurchaseInboundOrderStatusConstants;
import com.whoiszxl.constants.PurchaseOrderStatusConstants;
import com.whoiszxl.constants.WmsStockUpdateEventConstants;
import com.whoiszxl.dto.OrderInfoDTO;
import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.entity.PurchaseInboundOrder;
import com.whoiszxl.entity.schedule.SaleDeliveryScheduleResult;
import com.whoiszxl.feign.WmsFeignClient;
import com.whoiszxl.service.PurchaseInboundOrderItemService;
import com.whoiszxl.service.PurchaseInboundOrderService;
import com.whoiszxl.service.PurchaseOrderService;
import com.whoiszxl.stock.SaleDeliveryScheduler;
import com.whoiszxl.stock.WmsStockUpdater;
import com.whoiszxl.stock.WmsStockUpdaterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private SaleDeliveryScheduler saleDeliveryScheduler;

    @Autowired
    private WmsStockUpdaterFactory wmsStockUpdaterFactory;

    /**
     * 通过WMS中心，采购结算单审核已经通过了
     * @param purchaseInboundOrderId 采购入库单ID
     * @return 是否处理成功
     */
    @Override
    @PostMapping("/notifyFinishedPurchaseSettlementOrderEvent")
    public ResponseResult<Boolean> notifyFinishedPurchaseSettlementOrderEvent(Long purchaseInboundOrderId) {
        //1. 更新采购入库单状态为已完成
        purchaseInboundOrderService.updateStatus(purchaseInboundOrderId, PurchaseInboundOrderStatusConstants.FINISHED);

        //2. 更新采购单状态为已完成
        PurchaseInboundOrder purchaseInboundOrder = purchaseInboundOrderService.getById(purchaseInboundOrderId);
        purchaseOrderService.updateStatus(purchaseInboundOrder.getPurchaseOrderId(), PurchaseOrderStatusConstants.FINISHED);
        return ResponseResult.buildSuccess();
    }

    @Override
    @PostMapping("/notifyCreatePurchaseSettlementOrderEvent/{purchaseInboundOrderId}")
    public ResponseResult<Boolean> notifyCreatePurchaseSettlementOrderEvent(@PathVariable Long purchaseInboundOrderId) {
        //1. 更新采购入库单的状态为待结算
        PurchaseInboundOrderDTO purchaseInboundOrderDTO = purchaseInboundOrderService.getPurchaseInboundOrderById(purchaseInboundOrderId);
        purchaseInboundOrderService.updateStatus(purchaseInboundOrderId, PurchaseInboundOrderStatusConstants.WAIT_FOR_SETTLEMENT);

        //2. 更新采购单的状态为待结算
        purchaseOrderService.updateStatus(purchaseInboundOrderDTO.getPurchaseOrderId(), PurchaseOrderStatusConstants.WAIT_FOR_SETTLEMENT);
        return ResponseResult.buildSuccess();
    }

    @Override
    public ResponseResult<Boolean> notifyPayOrderSuccess(OrderInfoDTO orderInfo) {
        //更新WMS中心库存
        for (OrderItemDTO orderItemDTO : orderInfo.getOrderItemDTOList()) {
            SaleDeliveryScheduleResult scheduleResult = saleDeliveryScheduler.getScheduleResult(orderItemDTO);

            WmsStockUpdater stockUpdater = wmsStockUpdaterFactory.create(WmsStockUpdateEventConstants.PAY_ORDER, scheduleResult);
            stockUpdater.update();
        }

        //TODO 新增出库单，需要创建发货单，物流单，构建好出库单信息

        return null;
    }
}
