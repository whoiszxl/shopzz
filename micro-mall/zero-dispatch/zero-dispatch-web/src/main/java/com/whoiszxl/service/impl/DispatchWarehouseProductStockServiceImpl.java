package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.entity.DispatchWarehouseProductStock;
import com.whoiszxl.mapper.DispatchWarehouseProductStockMapper;
import com.whoiszxl.service.DispatchWarehouseProductStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 调度中心商品库存表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Service
public class DispatchWarehouseProductStockServiceImpl extends ServiceImpl<DispatchWarehouseProductStockMapper, DispatchWarehouseProductStock> implements DispatchWarehouseProductStockService {

    @Override
    public DispatchWarehouseProductStock getOrSave(Long productSkuId) {
        QueryWrapper queryWrapper = new QueryWrapper<DispatchWarehouseProductStock>();
        queryWrapper.eq("product_sku_id", productSkuId);
        DispatchWarehouseProductStock productStock = this.getOne(queryWrapper);

        if(productStock == null) {
            productStock = new DispatchWarehouseProductStock();
            productStock.setProductSkuId(productSkuId);
            productStock.setAvailableStockQuantity(0);
            productStock.setLockedStockQuantity(0);
            productStock.setDeliveriedStockQuantity(0);
            this.save(productStock);
        }
        return productStock;
    }
}
