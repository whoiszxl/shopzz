package com.whoiszxl.stock;

import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.entity.ProductStock;
import com.whoiszxl.service.ProductStockService;

import java.util.List;
import java.util.Map;

/**
 * 提交订单库存更新组件
 *
 * @author whoiszxl
 * @date 2021/8/2
 */
public class SubmitOrderStockUpdater extends AbstractStockUpdater {

    /**
     * SKUID -> 订单条目 的map集合
     */
    private Map<Long, OrderItemDTO> orderItemDTOMap;

    public SubmitOrderStockUpdater(ProductStockService productStockService, Map<Long, OrderItemDTO> orderItemDTOMap) {
        super(productStockService);
        this.orderItemDTOMap = orderItemDTOMap;
    }

    /**
     * 更新可销售库存，当会员下单后需要减去销售库存，加上锁定库存
     */
    @Override
    protected boolean updateSaleStockQuantityAndLockStock() {
        for (OrderItemDTO orderItem : orderItemDTOMap.values()) {
            boolean updateFlag = productStockService.subSaleStockAndAddLockStockBySkuId(orderItem.getQuantity(), orderItem.getSkuId());
            if(!updateFlag) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean updateSaledStockQuantity() {
        return true;
    }

    @Override
    protected boolean updateStockStatus() {
        return false;
    }
}
