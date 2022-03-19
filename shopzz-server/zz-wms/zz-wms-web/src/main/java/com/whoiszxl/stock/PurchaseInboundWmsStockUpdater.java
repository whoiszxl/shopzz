package com.whoiszxl.stock;

import com.whoiszxl.cqrs.response.PurchaseOrderResponse;
import com.whoiszxl.cqrs.vo.PurchaseOrderItemVO;
import com.whoiszxl.entity.WarehouseSkuStock;
import com.whoiszxl.service.WarehouseSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class PurchaseInboundWmsStockUpdater extends AbstractWmsStockUpdater {

    /**
     * 采购入库单
     */
    private PurchaseOrderResponse purchaseOrderResponse;

    @Autowired
    private WarehouseSkuStockService warehouseSkuStockService;

    /**
     * 更新商品库存
     */
    @Override
    protected void updateProductStock() {
        //1. 拿到采购入库单条目并进行遍历
        List<PurchaseOrderItemVO> items = purchaseOrderResponse.getItems();
        for (PurchaseOrderItemVO item : items) {
            //2. 从数据库中通过sku_id拿到当前的条目库存
            WarehouseSkuStock warehouseProductStock = warehouseSkuStockService.getOrSaveBySkuId(item.getSkuId());

            //3. 更新可用库存数量
            warehouseProductStock.setAvailableStockQuantity(warehouseProductStock.getAvailableStockQuantity() + item.getArrivalCount());
            warehouseSkuStockService.updateById(warehouseProductStock);
        }
    }

    @Override
    public void setParameter(Object parameter) {
        this.purchaseOrderResponse = (PurchaseOrderResponse) parameter;
    }
}
