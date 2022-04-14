package com.whoiszxl.state;

import com.whoiszxl.constants.OrderStatusConstants;
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

    @Autowired
    private CanceledOrderState canceledOrderState;

    @Autowired
    private WaitForDeliveryOrderState waitForDeliveryOrderState;

    @Autowired
    private WaitForReceiveOrderState waitForReceiveOrderState;

    @Autowired
    private FinishedOrderState finishedOrderState;

    @Autowired
    private WaitForReturnProductApproveOrderState waitForReturnProductApproveOrderState;

    @Autowired
    private DefaultOrderState defaultOrderState;

    /**
     * 创建订单，第一个状态就是待付款状态
     * @param order 订单信息
     */
    @Override
    public void create(Order order) {
        waitForPayOrderState.doTransition(order);
    }

    /**
     * 判断是否能够取消订单
     * @param order
     * @return
     */
    @Override
    public Boolean canCancel(Order order) {
        return getOrderState(order).canCancel(order);
    }

    /**
     * 将订单流转到取消状态
     * @param order
     */
    @Override
    public void cancel(Order order) {
        canceledOrderState.doTransition(order);
    }

    @Override
    public Boolean canPay(Order order) {
        return getOrderState(order).canPay(order);
    }

    @Override
    public void pay(Order order) {
        waitForDeliveryOrderState.doTransition(order);
    }

    @Override
    public void finishDelivery(Order order) {
        waitForReceiveOrderState.doTransition(order);
    }

    @Override
    public Boolean canConfirmReceipt(Order order) {
        return getOrderState(order).canConfirmReceipt(order);
    }

    @Override
    public void confirmReceipt(Order order) {
        finishedOrderState.doTransition(order);
    }

    /**
     * 获取订单状态组件
     * @param order 订单
     * @return 订单状态组件
     * @throws Exception
     */
    private OrderState getOrderState(Order order) {
        if(OrderStatusConstants.WAIT_FOR_PAY.equals(order.getOrderStatus())) {
            return waitForPayOrderState;
        } else if(OrderStatusConstants.CANCELED.equals(order.getOrderStatus())) {
            return canceledOrderState;
        } else if(OrderStatusConstants.WAIT_FOR_DELIVERY.equals(order.getOrderStatus())) {
            return waitForDeliveryOrderState;
        } else if(OrderStatusConstants.WAIT_FOR_RECEIVE.equals(order.getOrderStatus())) {
            return waitForReceiveOrderState;
        } else if(OrderStatusConstants.FINISHED.equals(order.getOrderStatus())) {
            return finishedOrderState;
        } else if(OrderStatusConstants.WAIT_FOR_RETURN_PRODUCT_APPROVE.equals(order.getOrderStatus())) {
            return waitForReturnProductApproveOrderState;
        }
        return defaultOrderState;
    }

}
