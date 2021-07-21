package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.entity.DispatchProductAllocationStock;
import com.whoiszxl.mapper.DispatchProductAllocationStockMapper;
import com.whoiszxl.service.DispatchProductAllocationStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 调度中心货位库存表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Service
public class DispatchProductAllocationStockServiceImpl extends ServiceImpl<DispatchProductAllocationStockMapper, DispatchProductAllocationStock> implements DispatchProductAllocationStockService {


    @Override
    public DispatchProductAllocationStock getOrSave(Long productAllocationId, Long productSkuId) {
        QueryWrapper queryWrapper = new QueryWrapper<DispatchProductAllocationStock>();
        queryWrapper.eq("product_allocation_id", productAllocationId);
        queryWrapper.eq("product_sku_id", productSkuId);
        DispatchProductAllocationStock allocationStock = this.getOne(queryWrapper);

        if(allocationStock == null) {
            allocationStock = new DispatchProductAllocationStock();
            allocationStock.setProductAllocationId(productAllocationId);
            allocationStock.setProductSkuId(productSkuId);
            allocationStock.setAvailableStockQuantity(0);
            allocationStock.setLockedStockQuantity(0);
            allocationStock.setDeliveriedStockQuantity(0);
            this.save(allocationStock);
        }
        return allocationStock;
    }
}
