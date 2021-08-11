package com.whoiszxl.stock;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.entity.ProductAllocationStockDetail;
import com.whoiszxl.entity.SaleDeliveryOrderPickingItem;
import com.whoiszxl.entity.schedule.SaleDeliveryScheduleResult;
import com.whoiszxl.service.ProductAllocationStockDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售出库调度器实现
 *
 * @author whoiszxl
 * @date 2021/8/11
 */
public class SaleDeliverySchedulerImpl implements SaleDeliveryScheduler {

    @Autowired
    private ProductAllocationStockDetailService productAllocationStockDetailService;

    @Override
    public SaleDeliveryScheduleResult schedule(OrderItemDTO orderItem) {
        SaleDeliveryScheduleResult scheduleResult = new SaleDeliveryScheduleResult();
        scheduleResult.setOrderItem(orderItem);

        //查询货位库存明细
        LambdaQueryWrapper<ProductAllocationStockDetail> queryWrapper =
                new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductAllocationStockDetail::getProductSkuId, orderItem.getSkuId());
        List<ProductAllocationStockDetail> stockDetailList = productAllocationStockDetailService.list(queryWrapper);

        Integer quantity = orderItem.getQuantity();

        Integer remainingSendOutQuantity = quantity;

        Map<Long, SaleDeliveryOrderPickingItem> pickingItems = new HashMap<>(100);

        for (ProductAllocationStockDetail stockDetail : stockDetailList) {
            //如果这个货位上的库存刚好可以满足发货就直接更新
            if(stockDetail.getCurrentStockQuantity() >= remainingSendOutQuantity) {
                updatePickingItem(stockDetail, orderItem.getSkuId(), remainingSendOutQuantity, pickingItems);
                break;
            }

            //如果不满足，则需要分批处理

            //将当前的wms库存货位上的sku数量全部上到拣货里
            updatePickingItem(stockDetail, orderItem.getSkuId(), stockDetail.getCurrentStockQuantity(), pickingItems);

            //剩余发货数量进行扣减
            remainingSendOutQuantity = remainingSendOutQuantity - stockDetail.getCurrentStockQuantity();
        }

        scheduleResult.setPickingItems(new ArrayList<>(pickingItems.values()));

        return scheduleResult;
    }

    /**
     * 更新拣货条目
     * @param stockDetail wms中的库存详细信息
     * @param skuId skuid
     * @param pickingCount 拣货数量
     * @param pickingItems 需要更新的拣货条目
     */
    private void updatePickingItem(ProductAllocationStockDetail stockDetail, Long skuId, Integer pickingCount, Map<Long, SaleDeliveryOrderPickingItem> pickingItems) {
        // 通过货位ID从需要拣货的条目中取出来
        SaleDeliveryOrderPickingItem pickingItem = pickingItems.get(stockDetail.getProductAllocationId());

        //如果不存在 ，则创建新增
        if(pickingItem == null) {
            pickingItem = createPickingItem(skuId, stockDetail.getProductAllocationId(), 0);
            pickingItems.put(stockDetail.getProductAllocationId(), pickingItem);
        }

        pickingItem.setPickingCount(pickingItem.getPickingCount() + pickingCount);
    }

    /**
     * 创建拣货条目
     * @param skuId sku id
     * @param productAllocationId 货位id
     * @param pickingCount 拣货数量
     * @return
     */
    private SaleDeliveryOrderPickingItem createPickingItem(Long skuId, Long productAllocationId, Integer pickingCount) {
        SaleDeliveryOrderPickingItem pickingItem = new SaleDeliveryOrderPickingItem();
        pickingItem.setProductAllocationId(productAllocationId);
        pickingItem.setSkuId(skuId);
        pickingItem.setPickingCount(pickingCount);
        return pickingItem;
    }

    @Override
    public SaleDeliveryScheduleResult getScheduleResult(OrderItemDTO orderItem) {
        return null;
    }
}
