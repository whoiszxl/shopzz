package com.whoiszxl.stock;

import com.whoiszxl.dto.PurchaseInboundOrderItemDTO;
import com.whoiszxl.entity.ProductStock;
import com.whoiszxl.service.ProductStockService;

import java.util.List;
import java.util.Map;

public class PurchaseInboundStockUpdater extends AbstractStockUpdater {

    private Map<Long, PurchaseInboundOrderItemDTO> itemDTOMap;


    public PurchaseInboundStockUpdater(List<ProductStock> productStocks, ProductStockService productStockService, Map<Long, PurchaseInboundOrderItemDTO> itemDTOMap) {
        super(productStocks, productStockService);
        this.itemDTOMap = itemDTOMap;
    }

    @Override
    protected boolean updateSaleStockQuantity() {

        for(ProductStock productStock : productStocks) {
            PurchaseInboundOrderItemDTO purchaseInboundOrderItemDTO = itemDTOMap.get(productStock.getProductSkuId());
            productStock.setSaleStockQuantity(productStock.getSaleStockQuantity() + purchaseInboundOrderItemDTO.getArrivalCount());
        }

        return true;
    }

    @Override
    protected boolean updateLockedStockQuantity() {
        return true;
    }

    @Override
    protected boolean updateSaledStockQuantity() {
        return true;
    }
}
