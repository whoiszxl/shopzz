package com.whoiszxl.stock;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.dto.ProductAllocationStockDetailDTO;
import com.whoiszxl.dto.PurchaseInboundOnItemDTO;
import com.whoiszxl.dto.PurchaseInboundOrderDTO;
import com.whoiszxl.dto.PurchaseInboundOrderItemDTO;
import com.whoiszxl.entity.ProductAllocationStock;
import com.whoiszxl.entity.ProductAllocationStockDetail;
import com.whoiszxl.entity.WarehouseProductStock;
import com.whoiszxl.service.ProductAllocationStockDetailService;
import com.whoiszxl.service.ProductAllocationStockService;
import com.whoiszxl.service.WarehouseProductStockService;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class PurchaseInboundWmsStockUpdater extends AbstractWmsStockUpdater {

    /**
     * 采购入库单
     */
    private PurchaseInboundOrderDTO purchaseInboundOrder;

    @Autowired
    private WarehouseProductStockService warehouseProductStockService;

    @Autowired
    private ProductAllocationStockService productAllocationStockService;

    @Autowired
    private ProductAllocationStockDetailService productAllocationStockDetailService;


    /**
     * 更新商品库存
     */
    @Override
    protected void updateProductStock() {
        //1. 拿到采购入库单条目并进行遍历
        List<PurchaseInboundOrderItemDTO> items = purchaseInboundOrder.getItems();
        for (PurchaseInboundOrderItemDTO item : items) {
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
        for (PurchaseInboundOrderItemDTO item : purchaseInboundOrder.getItems()) {

            //2. 再从入库单条目中拿到上架条目
            for (PurchaseInboundOnItemDTO onItemDTO : item.getOnItemDTOs()) {
                //3. 获取商品货位库存，如果货位不存在则新建一个
                ProductAllocationStock productAllocationStock = productAllocationStockService.getOrSave(onItemDTO.getProductAllocationId(), onItemDTO.getProductSkuId());

                //4. 累加可用库存数量并更新
                productAllocationStock.setAvailableStockQuantity(productAllocationStock.getAvailableStockQuantity() + onItemDTO.getPutOnShelvesCount());
                productAllocationStockService.updateById(productAllocationStock);
            }
        }
    }

    /**
     * 更新货位库存明细数据
     */
    @Override
    protected void updateProductAllocationStockDetail() {
        //1. 拿到采购入库单条目并进行遍历
        for (PurchaseInboundOrderItemDTO item : purchaseInboundOrder.getItems()) {

            List<ProductAllocationStockDetail> stockDetails = new ArrayList<>();

            //2. 拿到入库单条目中的上架条目进行遍历
            for (PurchaseInboundOnItemDTO onItemDTO : item.getOnItemDTOs()) {

                //3. 构建出库存详情对象并新增到数据库中
                ProductAllocationStockDetail stockDetail = new ProductAllocationStockDetail();
                stockDetail.setProductAllocationId(onItemDTO.getProductAllocationId());
                stockDetail.setProductSkuId(onItemDTO.getProductSkuId());
                stockDetail.setPutOnQuantity(onItemDTO.getPutOnShelvesCount());
                stockDetail.setPutOnTime(onItemDTO.getCreatedAt());
                stockDetail.setCurrentStockQuantity(stockDetail.getPutOnQuantity());
                stockDetail.setLockedStockQuantity(0L);

                productAllocationStockDetailService.save(stockDetail);
                stockDetails.add(stockDetail);
            }

            item.setStockDetails(BeanCopierUtils.copyListProperties(stockDetails, ProductAllocationStockDetailDTO::new));
        }

    }

    @Override
    public void setParameter(Object parameter) {
        this.purchaseInboundOrder = (PurchaseInboundOrderDTO) parameter;
    }
}
