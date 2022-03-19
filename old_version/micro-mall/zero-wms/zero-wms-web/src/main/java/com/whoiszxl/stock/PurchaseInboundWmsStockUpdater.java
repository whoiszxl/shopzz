package com.whoiszxl.stock;

import com.whoiszxl.dto.PurchaseInboundOnItemDTO;
import com.whoiszxl.dto.PurchaseOrderDTO;
import com.whoiszxl.dto.PurchaseOrderItemDTO;
import com.whoiszxl.entity.ProductAllocationStock;
import com.whoiszxl.entity.WarehouseProductStock;
import com.whoiszxl.service.ProductAllocationStockService;
import com.whoiszxl.service.WarehouseProductStockService;
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
    private PurchaseOrderDTO purchaseOrder;

    @Autowired
    private WarehouseProductStockService warehouseProductStockService;

    @Autowired
    private ProductAllocationStockService productAllocationStockService;


    /**
     * 更新商品库存
     */
    @Override
    protected void updateProductStock() {
        //1. 拿到采购入库单条目并进行遍历
        List<PurchaseOrderItemDTO> items = purchaseOrder.getItems();
        for (PurchaseOrderItemDTO item : items) {
            //2. 从数据库中通过sku_id拿到当前的条目库存
            WarehouseProductStock warehouseProductStock = warehouseProductStockService.getOrSaveBySkuId(item.getProductSkuId());

            //3. 更新可用库存数量
            warehouseProductStock.setAvailableStockQuantity(warehouseProductStock.getAvailableStockQuantity() + item.getArrivalCount());
            warehouseProductStockService.updateById(warehouseProductStock);
        }
    }

    /**
     * 更新商品货位库存
     */
    @Override
    protected void updateProductAllocationStock() {
        //1. 拿到采购入库单条目并进行遍历
        for (PurchaseOrderItemDTO item : purchaseOrder.getItems()) {

            //2. 再从入库单条目中拿到上架条目
            for (PurchaseInboundOnItemDTO onItemDTO : item.getOnItems()) {
                //3. 获取商品货位库存，如果货位不存在则新建一个
                ProductAllocationStock productAllocationStock = productAllocationStockService.getOrSave(onItemDTO.getProductAllocationId(), onItemDTO.getProductSkuId());

                //4. 累加可用库存数量并更新
                productAllocationStock.setAvailableStockQuantity(productAllocationStock.getAvailableStockQuantity() + onItemDTO.getPutOnShelvesCount());
                productAllocationStockService.updateById(productAllocationStock);
            }
        }
    }

    @Override
    public void setParameter(Object parameter) {
        this.purchaseOrder = (PurchaseOrderDTO) parameter;
    }
}
