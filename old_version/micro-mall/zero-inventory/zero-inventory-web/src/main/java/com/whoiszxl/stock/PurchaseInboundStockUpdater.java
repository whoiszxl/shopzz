package com.whoiszxl.stock;

import com.whoiszxl.constants.StockStatus;
import com.whoiszxl.dto.PurchaseOrderItemDTO;
import com.whoiszxl.entity.ProductStock;
import com.whoiszxl.service.ProductStockService;

import java.util.Map;

public class PurchaseInboundStockUpdater extends AbstractStockUpdater {

    private Map<Long, PurchaseOrderItemDTO> itemDTOMap;

    public PurchaseInboundStockUpdater(ProductStockService productStockService, Map<Long, PurchaseOrderItemDTO> itemDTOMap) {
        super(productStockService);
        this.itemDTOMap = itemDTOMap;
    }

    @Override
    protected boolean updateStock() {
        //采购入库，只要新增销售库存就OK
        for (PurchaseOrderItemDTO purchaseOrderItemDTO : itemDTOMap.values()) {
            //查询当前sku在库存中心是否存在，不存在则创建
            ProductStock productStock = productStockService.getProductStockBySkuId(purchaseOrderItemDTO.getProductSkuId());
            if(productStock == null) {
                productStock = new ProductStock();
                productStock.setProductSkuId(purchaseOrderItemDTO.getProductSkuId());
                productStock.setSaleStockQuantity(purchaseOrderItemDTO.getPurchaseQuantity());
                productStock.setLockedStockQuantity(0);
                productStock.setSaledStockQuantity(0);
                productStock.setStockStatus(StockStatus.IN_STOCK);
                productStockService.save(productStock);
            }else {
                productStockService.addSaleStock(purchaseOrderItemDTO.getPurchaseQuantity(), purchaseOrderItemDTO.getProductSkuId());
            }
        }
        return true;
    }

    @Override
    protected boolean updateStockStatus() {
        return false;
    }
}
