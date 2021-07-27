package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.entity.ProductAllocationStock;
import com.whoiszxl.mapper.ProductAllocationStockMapper;
import com.whoiszxl.service.ProductAllocationStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 货位库存表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Service
public class ProductAllocationStockServiceImpl extends ServiceImpl<ProductAllocationStockMapper, ProductAllocationStock> implements ProductAllocationStockService {

    /**
     * 获取商品货位库存，如果不存在则新建一条空记录
     * @param productAllocationId 商品货位ID
     * @param productSkuId 商品SKU ID
     * @return 商品货位库存
     */
    @Override
    public ProductAllocationStock getOrSave(Long productAllocationId, Long productSkuId) {
        //1. 从数据库中拿到商品库存记录
        QueryWrapper queryWrapper = new QueryWrapper<ProductAllocationStock>();
        queryWrapper.eq("product_allocation_id", productAllocationId);
        queryWrapper.eq("product_sku_id", productSkuId);
        ProductAllocationStock productAllocationStock = this.getOne(queryWrapper);

        if(productAllocationStock == null) {
            productAllocationStock = new ProductAllocationStock();
            productAllocationStock.setProductAllocationId(productAllocationId);
            productAllocationStock.setProductSkuId(productSkuId);
            productAllocationStock.setAvailableStockQuantity(0);
            productAllocationStock.setLockedStockQuantity(0);
            productAllocationStock.setDeliveriedStockQuantity(0);
            this.save(productAllocationStock);
        }
        return productAllocationStock;
    }
}
