package com.whoiszxl.state;

import com.whoiszxl.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单状态管理器接口实现
 */
@Component
public class OrderStateManagerImpl implements OrderStateManager {

    @Autowired
    private WaitForPayOrderState waitForPayOrderState;

    /**
     * 创建订单，第一个状态就是待付款状态
     * @param order 订单信息
     */
    @Override
    public void create(Order order) {
        waitForPayOrderState.doTransition(order);
    }

}
