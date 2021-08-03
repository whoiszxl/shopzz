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

    public SubmitOrderStockUpdater(List<ProductStock> productStocks, ProductStockService productStockService, Map<Long, OrderItemDTO> orderItemDTOMap) {
        super(productStocks, productStockService);
        this.orderItemDTOMap = orderItemDTOMap;
    }

    /**
     * 更新可销售库存，当会员下单后需要减去销售库存
     *
     * TODO 并发情况下这种情况更新会存在误差，需要优化
     */
    @Override
    protected boolean updateSaleStockQuantity() {
        for (ProductStock productStock : this.productStocks) {
            OrderItemDTO orderItemDTO = orderItemDTOMap.get(productStock.getProductSkuId());
            if(productStock.getSaleStockQuantity() - orderItemDTO.getQuantity() < 0) {
                return false;
            }
            productStock.setSaleStockQuantity(productStock.getSaleStockQuantity() - orderItemDTO.getQuantity());
        }
        return true;
    }

    /**
     * 更新锁定库存，当会员下单后需要加上销售库存
     */
    @Override
    protected boolean updateLockedStockQuantity() {
        for (ProductStock productStock : this.productStocks) {
            OrderItemDTO orderItemDTO = orderItemDTOMap.get(productStock.getProductSkuId());
            productStock.setLockedStockQuantity(productStock.getLockedStockQuantity() + orderItemDTO.getQuantity());
        }
        return true;
    }

    @Override
    protected boolean updateSaledStockQuantity() {
        return true;
    }
}
