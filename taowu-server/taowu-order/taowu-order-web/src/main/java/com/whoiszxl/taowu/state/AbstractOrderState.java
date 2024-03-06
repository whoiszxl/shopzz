package com.whoiszxl.taowu.state;

import com.whoiszxl.taowu.entity.Order;
import com.whoiszxl.taowu.mapper.OrderMapper;

/**
 * 订单状态的抽象基类
 */
public abstract class AbstractOrderState implements OrderState {

    private OrderMapper orderMapper;

    public AbstractOrderState(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /**
     * 待实现，获取订单的状态
     * @param order 订单信息
     * @return 订单状态码
     */
    protected abstract Integer getOrderStatus(Order order);

    @Override
    public void doTransition(Order order) {
        Integer orderStatus = getOrderStatus(order);
        order.setOrderStatus(orderStatus);

        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setOrderStatus(orderStatus);
        orderMapper.updateById(updateOrder);
    }

    @Override
    public Boolean canPay(Order order) {
        return false;
    }

    @Override
    public Boolean canConfirmReceipt(Order order) {
        return false;
    }

    @Override
    public Boolean canCancel(Order order) {
        return false;
    }

    @Override
    public Boolean canApplyReturnProduct(Order order) {
        return false;
    }
}
