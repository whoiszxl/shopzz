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

/**
 * 提交订单库存更新者
 *
 * @author whoiszxl
 * @date 2021/8/11
 */
@Component
@Scope("prototype")
public class SubmitOrderWmsStockUpdater extends AbstractWmsStockUpdater {

    /**
     * 调度结果
     */
    private SaleDeliveryScheduleResult scheduleResult;

    @Autowired
    private WarehouseProductStockService warehouseProductStockService;

    @Autowired
    private ProductAllocationStockService productAllocationStockService;


    @Override
    protected void updateProductStock() {
        //更新商品库存
        OrderItemDTO orderItem = scheduleResult.getOrderItem();
        //提交订单，减去可用，增加锁定
        warehouseProductStockService.subAvailableStockAndAddLockedStock(orderItem.getQuantity(), orderItem.getSkuId());
    }

    @Override
    protected void updateProductAllocationStock() {
        List<SaleDeliveryOrderPickingItem> pickingItems = scheduleResult.getPickingItems();
        for (SaleDeliveryOrderPickingItem pickingItem : pickingItems) {
            //货位库存减去可用，增加锁定
            productAllocationStockService.subAvailableStockAndAddLockedStock(
                    pickingItem.getPickingCount(),
                    pickingItem.getProductAllocationId(),
                    pickingItem.getSkuId());
        }
    }

    @Override
    public void setParameter(Object parameter) {
        this.scheduleResult = (SaleDeliveryScheduleResult) parameter;
    }
}
