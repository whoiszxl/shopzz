package com.whoiszxl.stock;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.dto.PurchaseInboundOrderItemDTO;
import com.whoiszxl.entity.ProductStock;
import com.whoiszxl.service.ProductStockService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author whoiszxl
 * @date 2021/7/21
 */
@Component
public class PurchaseInboundStockUpdaterFactory<T> extends AbstractStockUpdaterFactory<T> {

    /**
     * 注入商品库存服务
     * @param productStockService 商品库存服务
     */
    @Autowired
    public PurchaseInboundStockUpdaterFactory(ProductStockService productStockService) {
        super(productStockService);
    }

    /**
     * 获取商品skuid集合
     * @param parameter 参数
     * @return 商品sku id集合
     */
    @Override
    protected List<Long> getProductSkuIds(T parameter) {
        PurchaseInboundOrderDTO purchaseInboundOrderDTO = (PurchaseInboundOrderDTO) parameter;
        List<PurchaseInboundOrderItemDTO> inboundOrderDTOItems = purchaseInboundOrderDTO.getItems();

        if(ObjectUtils.isEmpty(inboundOrderDTOItems)) {
            return new ArrayList<>();
        }

        List<Long> productSkuIds = new ArrayList<>(inboundOrderDTOItems.size());
        for (PurchaseInboundOrderItemDTO inboundOrderDTOItem : inboundOrderDTOItems) {
            productSkuIds.add(inboundOrderDTOItem.getProductSkuId());
        }

        return productSkuIds;
    }

    /**
     * 创建库存更新命令
     * @param productStocks 商品库存对象集合
     * @param parameter 参数
     * @return 库存更新命令
     */
    @Override
    protected StockUpdater create(List<ProductStock> productStocks, T parameter) {
        PurchaseInboundOrderDTO purchaseInboundOrderDTO = (PurchaseInboundOrderDTO) parameter;
        List<PurchaseInboundOrderItemDTO> inboundOrderDTOItems = purchaseInboundOrderDTO.getItems();

        Map<Long, PurchaseInboundOrderItemDTO> itemDTOMap = new HashMap<>(100);

        if(ObjectUtils.isNotEmpty(inboundOrderDTOItems)) {
            for (PurchaseInboundOrderItemDTO inboundOrderDTOItem : inboundOrderDTOItems) {
                itemDTOMap.put(inboundOrderDTOItem.getProductSkuId(), inboundOrderDTOItem);
            }
        }

        return new PurchaseInboundStockUpdater(productStocks, productStockService, itemDTOMap);
    }
}
