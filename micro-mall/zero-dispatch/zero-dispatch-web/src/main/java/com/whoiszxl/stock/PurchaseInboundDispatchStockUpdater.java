package com.whoiszxl.stock;

import com.whoiszxl.dto.PurchaseInboundOnItemDTO;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.dto.PurchaseInboundOrderItemDTO;
import com.whoiszxl.entity.DispatchProductAllocationStock;
import com.whoiszxl.entity.DispatchWarehouseProductStock;
import com.whoiszxl.service.DispatchProductAllocationStockService;
import com.whoiszxl.service.DispatchWarehouseProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 调度中心 采购入库库存更新组件
 */
@Component
@Scope("prototype")
public class PurchaseInboundDispatchStockUpdater extends AbstractDispatchStockUpdater {

    private PurchaseInboundOrderDTO purchaseInboundOrder;

    @Autowired
    private DispatchWarehouseProductStockService productStockService;

    @Autowired
    private DispatchProductAllocationStockService allocationStockService;

    /**
     * 更新商品库存
     */
    @Override
    protected void updateProductStock() {
        //1. 遍历采购入库单中的条目
        for (PurchaseInboundOrderItemDTO item : purchaseInboundOrder.getItems()) {
            //2. 查询数据库中调度中心的商品库存
            DispatchWarehouseProductStock productStock = productStockService.getOrSave(item.getProductSkuId());

            //3. 累加并更新
            productStock.setAvailableStockQuantity(productStock.getAvailableStockQuantity() + item.getArrivalCount());
            productStockService.updateById(productStock);
        }

    }

    /**
     * 更新货位库存
     */
    @Override
    protected void updateProductAllocationStock() {
        //1. 遍历采购入库单中的条目，遍历条目中的上架条目
        for (PurchaseInboundOrderItemDTO item : purchaseInboundOrder.getItems()) {
            for (PurchaseInboundOnItemDTO onItemDTO : item.getOnItemDTOs()) {
                //2. 通过货位id和skuid获取到货位库存信息，不存在则新建
                DispatchProductAllocationStock allocationStock = allocationStockService.getOrSave(onItemDTO.getProductAllocationId(), onItemDTO.getProductSkuId());

                //3. 累加可用库存数量并更新
                allocationStock.setAvailableStockQuantity(allocationStock.getAvailableStockQuantity() + onItemDTO.getPutOnShelvesCount());
                allocationStockService.updateById(allocationStock);
            }
        }
    }

    @Override
    public void setParameter(Object parameter) {
        this.purchaseInboundOrder = (PurchaseInboundOrderDTO) parameter;
    }
}
