package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.entity.ProductStock;
import com.whoiszxl.mapper.ProductStockMapper;
import com.whoiszxl.service.ProductStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存中心的商品库存表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-21
 */
@Service
public class ProductStockServiceImpl extends ServiceImpl<ProductStockMapper, ProductStock> implements ProductStockService {

    @Autowired
    private ProductStockMapper productStockMapper;
    
    @Override
    public ProductStock getProductStockBySkuId(Long skuId) {
        QueryWrapper queryWrapper = new QueryWrapper<ProductStock>();
        queryWrapper.eq("product_sku_id", skuId);
        return this.getOne(queryWrapper);
    }


    @Override
    public void updateProductStock(ProductStock productStock) {
        this.updateById(productStock);
    }

    @Override
    public boolean subSaleStockAndAddLockStockBySkuId(Integer quantity, Long skuId) {
        return productStockMapper.subSaleStockAndAddLockStockBySkuId(quantity, skuId);
    }

    @Override
    public boolean addSaleStock(Integer purchaseQuantity, Long productSkuId) {
        return productStockMapper.addSaleStock(purchaseQuantity, productSkuId);
    }


    @Override
    public boolean subLockStockAndAddSaledStockBySkuId(Integer quantity, Long skuId) {
        return productStockMapper.subLockStockAndAddSaledStockBySkuId(quantity, skuId);
    }
}
