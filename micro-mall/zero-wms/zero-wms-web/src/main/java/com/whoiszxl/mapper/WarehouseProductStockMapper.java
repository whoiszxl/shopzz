package com.whoiszxl.mapper;

import com.whoiszxl.entity.WarehouseProductStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 仓库商品库存表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
public interface WarehouseProductStockMapper extends BaseMapper<WarehouseProductStock> {

    /**
     * 减去可用库存，增加锁定库存
     * @param quantity 更新数量
     * @param skuId sku id
     * @return 是否更新成功
     */
    @Update("update wms_warehouse_product_stock " +
            "set available_stock_quantity = available_stock_quantity - #{quantity}," +
            "locked_stock_quantity = locked_stock_quantity + #{quantity} " +
            "where product_sku_id = #{skuId} " +
            "and available_stock_quantity >= #{quantity}")
    boolean subAvailableStockAndAddLockedStock(Integer quantity, Long skuId);

    @Update("update wms_warehouse_product_stock " +
            "set deliveried_stock_quantity = deliveried_stock_quantity + #{quantity}," +
            "locked_stock_quantity = locked_stock_quantity - #{quantity} " +
            "where product_sku_id = #{skuId} " +
            "and locked_stock_quantity >= #{quantity}")
    boolean subLockedStockAndAddDeliveriedStock(Integer quantity, Long skuId);
}
