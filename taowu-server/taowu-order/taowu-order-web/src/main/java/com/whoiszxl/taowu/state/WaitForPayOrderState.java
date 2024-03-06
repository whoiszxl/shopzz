package com.whoiszxl.taowu.state;

import com.whoiszxl.taowu.constants.OrderStatusConstants;
import com.whoiszxl.taowu.entity.Order;
import com.whoiszxl.taowu.mapper.OrderMapper;
import org.springframework.stereotype.Component;

/**
 * 待付款状态
 * 订单状态为待支付 - 1
 * 可支付，可取消
 */
@Component
public class WaitForPayOrderState extends AbstractOrderState {

    public WaitForPayOrderState(OrderMapper orderMapper) {
        super(orderMapper);
    }

    @Override
    protected Integer getOrderStatus(Order order) {
        return OrderStatusConstants.WAIT_FOR_PAY;
    }

    @Override
    public Boolean canPay(Order order) {
        return true;
    }

    @Override
    public Boolean canCancel(Order order) {
        return true;
    }

    @Override
    public Boolean canConfirmReceipt(Order order) {
        return false;
    }

    @Override
    public Boolean canApplyReturnProduct(Order order) {
        return false;
    }
}
