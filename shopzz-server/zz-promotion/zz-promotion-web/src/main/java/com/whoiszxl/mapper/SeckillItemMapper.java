package com.whoiszxl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.entity.SeckillItem;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 秒杀item表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
public interface SeckillItemMapper extends BaseMapper<SeckillItem> {

    @Update("update spms_seckill_item " +
            "set available_stock_quantity = available_stock_quantity - #{quantity} " +
            "where id = #{seckillItemId} " +
            "and available_stock_quantity >= #{quantity}")
    boolean subDbStock(Long seckillItemId, Integer quantity);

    @Update("update spms_seckill_item " +
            "set available_stock_quantity = available_stock_quantity + #{quantity} " +
            "where id = #{seckillItemId} " +
            "and available_stock_quantity + #{quantity} <= init_stock_quantity")
    boolean addDbStock(Long seckillItemId, Integer quantity);
}
