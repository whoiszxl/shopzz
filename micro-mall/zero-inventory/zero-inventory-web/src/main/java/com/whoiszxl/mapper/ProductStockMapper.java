package com.whoiszxl.mapper;

import com.whoiszxl.entity.ProductStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 库存中心的商品库存表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-21
 */
public interface ProductStockMapper extends BaseMapper<ProductStock> {

    @Update("update inventory_product_stock " +
            "set sale_stock_quantity - #{quantity}, locked_stock_quantity + #{quantity} " +
            "where product_sku_id = #{skuId} " +
            "and sale_stock_quantity >= #{quantity}")
    boolean subSaleStockAndAddLockStockBySkuId(Integer quantity, Long skuId);

    @Update("update inventory_product_stock " +
            "set sale_stock_quantity + #{purchaseQuantity} " +
            "where product_sku_id = #{productSkuId} " +
            "")
    boolean addSaleStock(Integer purchaseQuantity, Long productSkuId);
}
