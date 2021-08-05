package com.whoiszxl.stock;

import com.whoiszxl.dto.PurchaseInboundOrderItemDTO;
import com.whoiszxl.service.ProductStockService;

import java.util.Map;

public class PurchaseInboundStockUpdater extends AbstractStockUpdater {

    private Map<Long, PurchaseInboundOrderItemDTO> itemDTOMap;

    public PurchaseInboundStockUpdater(ProductStockService productStockService, Map<Long, PurchaseInboundOrderItemDTO> itemDTOMap) {
        super(productStockService);
        this.itemDTOMap = itemDTOMap;
    }

    @Override
    protected boolean updateSaleStockQuantityAndLockStock() {
        //采购入库，只要新增销售库存就OK
        for (PurchaseInboundOrderItemDTO inboundOrderItemDTO : itemDTOMap.values()) {
            boolean addFlag = productStockService.addSaleStock(inboundOrderItemDTO.getPurchaseQuantity(), inboundOrderItemDTO.getProductSkuId());
            if(!addFlag) {
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
