package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.entity.WarehouseProductStock;
import com.whoiszxl.mapper.WarehouseProductStockMapper;
import com.whoiszxl.service.WarehouseProductStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 仓库商品库存表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Service
public class WarehouseProductStockServiceImpl extends ServiceImpl<WarehouseProductStockMapper, WarehouseProductStock> implements WarehouseProductStockService {

    @Override
    public WarehouseProductStock getOrSaveBySkuId(Long productSkuId) {
        WarehouseProductStock warehouseProductStock = this.getOne(new QueryWrapper<WarehouseProductStock>()
                .eq("product_sku_id", productSkuId));
        if(warehouseProductStock == null) {
            warehouseProductStock = new WarehouseProductStock();
            warehouseProductStock.setProductSkuId(productSkuId);
            warehouseProductStock.setAvailableStockQuantity(0);
            warehouseProductStock.setLockedStockQuantity(0);
            warehouseProductStock.setDeliveriedStockQuantity(0);
            this.save(warehouseProductStock);
        }

        return warehouseProductStock;
    }
}
