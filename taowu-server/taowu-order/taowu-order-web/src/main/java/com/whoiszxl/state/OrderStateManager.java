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

    /**
     * 订单是否可以取消
     * @param order
     * @return
     */
    Boolean canCancel(Order order);


    /**
     * 取消订单操作
     * @param order
     */
    void cancel(Order order);

    /**
     * 判断订单能否进行支付
     * @param order
     * @return
     */
    Boolean canPay(Order order);


    /**
     * 执行支付订单操作
     * @param order
     */
    void pay(Order order);

    /**
     * 完成商品发货
     * @param order
     */
    void finishDelivery(Order order);

    /**
     * 判断能否执行确认收货的操作
     * @param order
     * @return
     */
    Boolean canConfirmReceipt(Order order);

    /**
     * 确认收货
     * @param order
     */
    void confirmReceipt(Order order);
}
