package com.whoiszxl.stock;

import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.entity.WarehouseProductStock;
import com.whoiszxl.entity.schedule.SaleDeliveryScheduleResult;
import com.whoiszxl.service.ProductAllocationStockDetailService;
import com.whoiszxl.service.ProductAllocationStockService;
import com.whoiszxl.service.WarehouseProductStockService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 提交订单库存更新者
 *
 * @author whoiszxl
 * @date 2021/8/11
 */
public class SubmitOrderWmsStockUpdater extends AbstractWmsStockUpdater {

    /**
     * 调度结果
     */
    private SaleDeliveryScheduleResult scheduleResult;

    @Autowired
    private WarehouseProductStockService warehouseProductStockService;

    @Autowired
    private ProductAllocationStockService productAllocationStockService;

    @Autowired
    private ProductAllocationStockDetailService productAllocationStockDetailService;


    @Override
    protected void updateProductStock() {
        //更新商品库存
        OrderItemDTO orderItem = scheduleResult.getOrderItem();

        //获取在wms中的库存
        WarehouseProductStock productStock = warehouseProductStockService.getOrSaveBySkuId(orderItem.getSkuId());

        //TODO 减去可用库存，加上锁定库存

    }

    @Override
    protected void updateProductAllocationStock() {

    }

    @Override
    protected void updateProductAllocationStockDetail() {

    }

    @Override
    public void setParameter(Object parameter) {
        this.scheduleResult = (SaleDeliveryScheduleResult) parameter;
    }
}
