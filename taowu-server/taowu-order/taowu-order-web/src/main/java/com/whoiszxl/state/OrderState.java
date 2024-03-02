package com.whoiszxl.state;

import com.whoiszxl.entity.Order;

/**
 *订单状态接口
 */
public interface OrderState {

    /**
     * 订单流转到当前这个状态
     * @param order 订单信息
     */
    void doTransition(Order order);


    /**
     * 判断当前状态的订单是否能取消
     * @param order 订单信息
     * @return 是否能取消
     */
    Boolean canCancel(Order order);

    /**
     * 判断当前状态的订单是否能支付
     * @param order 订单信息
     * @return 是否能支付
     */
    Boolean canPay(Order order);


    /**
     * 判断是否能执行手动确认收货的操作
     * @param order 订单信息
     * @return 是否能手动确认收货
     */
    Boolean canConfirmReceipt(Order order);

    /**
     * 判断能否申请退货
     * @param order 订单信息
     * @return 是否能退货
     */
    Boolean canApplyReturnProduct(Order order);
}
