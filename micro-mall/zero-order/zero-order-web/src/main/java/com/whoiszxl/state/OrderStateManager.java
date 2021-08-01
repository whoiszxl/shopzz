package com.whoiszxl.state;

import com.whoiszxl.entity.Order;

/**
 * 订单状态管理器的接口
 */
public interface OrderStateManager {

    /**
     * 创建订单
     * @param order 订单信息
     */
    void create(Order order);
}
