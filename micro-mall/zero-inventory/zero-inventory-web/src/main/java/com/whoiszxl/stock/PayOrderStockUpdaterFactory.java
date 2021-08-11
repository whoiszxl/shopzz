package com.whoiszxl.stock;

import com.whoiszxl.dto.OrderDTO;
import com.whoiszxl.dto.OrderInfoDTO;
import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付订单库存更新工厂
 *
 * @author whoiszxl
 * @date 2021/8/11
 */
@Component
public class PayOrderStockUpdaterFactory<T> extends AbstractStockUpdaterFactory<T> {

    @Autowired
    public PayOrderStockUpdaterFactory(ProductStockService productStockService) {
        super(productStockService);
    }

    @Override
    protected List<Long> getProductSkuIds(T parameter) {
        OrderInfoDTO orderInfoDTO = (OrderInfoDTO) parameter;
        List<Long> productSkuIds = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderInfoDTO.getOrderItemDTOList()) {
            productSkuIds.add(orderItemDTO.getSkuId());
        }
        return productSkuIds;
    }

    @Override
    protected StockUpdater createCommand(T parameter) {
        OrderInfoDTO orderInfoDTO = (OrderInfoDTO) parameter;

        Map<Long, OrderItemDTO> orderItemDTOMap = new HashMap<>(100);

        for (OrderItemDTO orderItemDTO : orderInfoDTO.getOrderItemDTOList()) {
            orderItemDTOMap.put(orderItemDTO.getSkuId(), orderItemDTO);
        }

        return new PayOrderStockUpdater(productStockService, orderItemDTOMap);
    }
}
