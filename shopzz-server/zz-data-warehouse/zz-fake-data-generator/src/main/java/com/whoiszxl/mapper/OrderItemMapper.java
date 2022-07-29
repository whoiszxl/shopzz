package com.whoiszxl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.entity.order.Order;
import com.whoiszxl.entity.order.OrderItem;

import java.util.Collection;


/**
 * <p>
 * 订单明细表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    /**
     * 批量插入（mysql）
     * @param entityList
     * @return
     */
    Integer insertBatchSomeColumn(Collection<OrderItem> entityList);
}
