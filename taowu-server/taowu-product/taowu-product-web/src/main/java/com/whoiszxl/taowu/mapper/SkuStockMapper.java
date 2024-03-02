package com.whoiszxl.taowu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.taowu.entity.SkuStock;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 商品SKU库存表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
public interface SkuStockMapper extends BaseMapper<SkuStock> {

    @Update("update pms_sku_stock " +
            "set sale_stock_quantity = sale_stock_quantity - #{quantity}," +
            "locked_stock_quantity = locked_stock_quantity + #{quantity} " +
            "where sku_id = #{skuId} " +
            "and sale_stock_quantity >= #{quantity}")
    int subSaleStockAndAddLockStockBySkuId(Long skuId, Integer quantity);


    @Update("update pms_sku_stock " +
            "set sale_stock_quantity = sale_stock_quantity + #{quantity} " +
            "where sku_id = #{skuId} ")
    boolean addSaleStock(Long skuId, Integer quantity);

    @Update("update pms_sku_stock " +
            "set locked_stock_quantity = locked_stock_quantity - #{quantity}," +
            "saled_stock_quantity = saled_stock_quantity + #{quantity} " +
            "where sku_id = #{skuId} " +
            "and locked_stock_quantity >= #{quantity}")
    boolean subLockStockAndAddSaledStockBySkuId(Long skuId, Integer quantity);
}
