package com.whoiszxl.stock;

import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.entity.WarehouseProductStock;
import com.whoiszxl.service.ProductAllocationStockService;
import com.whoiszxl.service.WarehouseProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PurchaseInboundWmsStockUpdater extends AbstractWmsStockUpdater {

    /**
     * 采购入库单
     */
    private PurchaseInboundOrderDTO purchaseInboundOrderDTO;

    @Autowired
    private WarehouseProductStockService warehouseProductStockService;

    @Autowired
    private ProductAllocationStockService productAllocationStockService;

//    @Autowired
//    private ProductAllocationStockDetailService productAllocationStockDetailService;


    @Override
    protected void updateProductStock() {

    }

    @Override
    protected void updateProductAllocationStock() {

    }

    @Override
    protected void updateProductAllocationStockDetail() {

    }

    @Override
    public void setParameter(Object parameter) {
        this.purchaseInboundOrderDTO = (PurchaseInboundOrderDTO) parameter;
    }
}
