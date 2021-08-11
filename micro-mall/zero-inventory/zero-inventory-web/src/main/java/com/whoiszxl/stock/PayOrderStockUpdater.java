package com.whoiszxl.stock;

import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.service.ProductStockService;

import java.util.Map;

/**
 * 支付订单库存更新者
 *
 * @author whoiszxl
 * @date 2021/8/11
 */
public class PayOrderStockUpdater extends AbstractStockUpdater {

    /**
     * 订单条目DTO对象集合
     */
    private Map<Long, OrderItemDTO> orderItemDTOMap;

    public PayOrderStockUpdater(ProductStockService productStockService, Map<Long, OrderItemDTO> orderItemDTOMap) {
        super(productStockService);
        this.orderItemDTOMap = orderItemDTOMap;
    }


    /**
     * 支付订单成功了，需要将锁定的库存减去
     * @return
     */
    @Override
    protected boolean updateStock() {

        for (OrderItemDTO orderItem : orderItemDTOMap.values()) {
            boolean updateFlag = productStockService.subLockStockAndAddSaledStockBySkuId(orderItem.getQuantity(), orderItem.getSkuId());
            if(!updateFlag) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean updateStockStatus() {
        return false;
    }
}
