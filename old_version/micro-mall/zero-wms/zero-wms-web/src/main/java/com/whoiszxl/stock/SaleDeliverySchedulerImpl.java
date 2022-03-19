package com.whoiszxl.stock;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.entity.ProductAllocationStock;
import com.whoiszxl.entity.SaleDeliveryOrderPickingItem;
import com.whoiszxl.entity.schedule.SaleDeliveryScheduleResult;
import com.whoiszxl.service.ProductAllocationStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class SaleDeliverySchedulerImpl implements SaleDeliveryScheduler {

    @Autowired
    private ProductAllocationStockService productAllocationStockService;

    @Override
    public SaleDeliveryScheduleResult schedule(OrderItemDTO orderItem) {
        SaleDeliveryScheduleResult scheduleResult = new SaleDeliveryScheduleResult();
        scheduleResult.setOrderItem(orderItem);

        //查询货位库存明细
        LambdaQueryWrapper<ProductAllocationStock> queryWrapper =
                new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductAllocationStock::getProductSkuId, orderItem.getSkuId());
        List<ProductAllocationStock> stockList = productAllocationStockService.list(queryWrapper);

        Integer remainingSendOutQuantity = orderItem.getQuantity();

        Map<Long, SaleDeliveryOrderPickingItem> pickingItems = new HashMap<>(100);

        for (ProductAllocationStock stock : stockList) {
            //如果这个货位上的库存刚好可以满足发货就直接更新
            if(stock.getAvailableStockQuantity() >= remainingSendOutQuantity) {
                updatePickingItem(stock, orderItem.getSkuId(), remainingSendOutQuantity, pickingItems);
                break;
            }

            //如果不满足，则需要分批处理

            //将当前的wms库存货位上的sku数量全部上到拣货里
            updatePickingItem(stock, orderItem.getSkuId(), stock.getAvailableStockQuantity(), pickingItems);

            //剩余发货数量进行扣减
            remainingSendOutQuantity = remainingSendOutQuantity - stock.getAvailableStockQuantity();
        }

        scheduleResult.setPickingItems(new ArrayList<>(pickingItems.values()));

        return scheduleResult;
    }

    /**
     * 更新拣货条目
     * @param stock wms中的库存详细信息
     * @param skuId skuid
     * @param pickingCount 拣货数量
     * @param pickingItems 需要更新的拣货条目
     */
    private void updatePickingItem(ProductAllocationStock stock, Long skuId, Integer pickingCount, Map<Long, SaleDeliveryOrderPickingItem> pickingItems) {
        // 通过货位ID从需要拣货的条目中取出来
        SaleDeliveryOrderPickingItem pickingItem = pickingItems.get(stock.getProductAllocationId());

        //如果不存在 ，则创建新增
        if(pickingItem == null) {
            pickingItem = createPickingItem(skuId, stock.getProductAllocationId(), 0);
            pickingItems.put(stock.getProductAllocationId(), pickingItem);
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
