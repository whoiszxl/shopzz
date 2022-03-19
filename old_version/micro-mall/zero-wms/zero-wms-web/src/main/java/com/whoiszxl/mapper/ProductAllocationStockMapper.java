package com.whoiszxl.mapper;

import com.whoiszxl.entity.ProductAllocationStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 货位库存表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
public interface ProductAllocationStockMapper extends BaseMapper<ProductAllocationStock> {

    @Update("update wms_product_allocation_stock " +
            "set available_stock_quantity = available_stock_quantity - #{pickingCount}, " +
            "locked_stock_quantity = locked_stock_quantity + #{pickingCount} " +
            "where product_sku_id = #{skuId} " +
            "and available_stock_quantity >= #{pickingCount}")
    boolean subAvailableStockAndAddLockedStock(Integer pickingCount, Long productAllocationId, Long skuId);

    @Update("update wms_product_allocation_stock " +
            "set deliveried_stock_quantity = deliveried_stock_quantity + #{pickingCount}, " +
            "locked_stock_quantity = locked_stock_quantity - #{pickingCount} " +
            "where product_sku_id = #{skuId} " +
            "and locked_stock_quantity >= #{pickingCount}")
    boolean subLockedStockAndAddDeliveriedStock(Integer pickingCount, Long productAllocationId, Long skuId);
}
