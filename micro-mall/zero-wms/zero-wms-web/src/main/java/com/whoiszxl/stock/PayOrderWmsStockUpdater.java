package com.whoiszxl.stock;

import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.entity.SaleDeliveryOrderPickingItem;
import com.whoiszxl.entity.schedule.SaleDeliveryScheduleResult;
import com.whoiszxl.service.ProductAllocationStockService;
import com.whoiszxl.service.WarehouseProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class PayOrderWmsStockUpdater extends AbstractWmsStockUpdater {

    private SaleDeliveryScheduleResult scheduleResult;

    @Autowired
    private WarehouseProductStockService warehouseProductStockService;

    @Autowired
    private ProductAllocationStockService productAllocationStockService;

    @Override
    protected void updateProductStock() {
        OrderItemDTO orderItem = scheduleResult.getOrderItem();
        //支付成功，减去锁定，加上出库
        warehouseProductStockService.subLockedStockAndAddDeliveriedStock(orderItem.getQuantity(), orderItem.getSkuId());
    }

    @Override
    protected void updateProductAllocationStock() {
        List<SaleDeliveryOrderPickingItem> pickingItems = scheduleResult.getPickingItems();

        for (SaleDeliveryOrderPickingItem pickingItem : pickingItems) {
            productAllocationStockService.subLockedStockAndAddDeliveriedStock(pickingItem.getPickingCount(), pickingItem.getProductAllocationId(), pickingItem.getSkuId());
        }

    }

    @Override
    public void setParameter(Object parameter) {
        this.scheduleResult = (SaleDeliveryScheduleResult) parameter;
    }
}
