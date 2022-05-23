package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.WarehouseSkuStock;
import com.whoiszxl.mapper.WarehouseSkuStockMapper;
import com.whoiszxl.service.WarehouseSkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 仓储sku库存服务实现
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@Slf4j
@Service
public class WarehouseSkuStockServiceImpl extends ServiceImpl<WarehouseSkuStockMapper, WarehouseSkuStock> implements WarehouseSkuStockService {


    @Override
    public WarehouseSkuStock getOrSaveBySkuId(Long skuId) {
        WarehouseSkuStock warehouseProductStock = this.getOne(new LambdaQueryWrapper<WarehouseSkuStock>()
                .eq(WarehouseSkuStock::getSkuId, skuId));
        if(warehouseProductStock == null) {
            warehouseProductStock = new WarehouseSkuStock();
            warehouseProductStock.setSkuId(skuId);
            warehouseProductStock.setAvailableStockQuantity(0);
            warehouseProductStock.setLockedStockQuantity(0);
            warehouseProductStock.setWarnStockQuantity(0);
            this.save(warehouseProductStock);
        }

        return warehouseProductStock;
    }
}
