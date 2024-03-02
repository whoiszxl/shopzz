package com.whoiszxl.state;


import com.whoiszxl.constants.OrderOperateTypeConstants;
import com.whoiszxl.entity.Order;
import com.whoiszxl.factory.OrderOperateHistoryFactory;
import com.whoiszxl.service.OrderOperateHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 带操作日志记录功能的订单状态管理器
 */
@Component
@RequiredArgsConstructor
public class LoggerOrderStateManager implements OrderStateManager {

    private final OrderStateManagerImpl orderStateManager;

    private final OrderOperateHistoryService orderOperateHistoryService;

    private final OrderOperateHistoryFactory orderOperateHistoryFactory;

    @Override
    public void create(Order order) {
        orderStateManager.create(order);
        orderOperateHistoryService.save(orderOperateHistoryFactory.get(order, OrderOperateTypeConstants.CREATE_ORDER));
    }

    @Override
    public Boolean canCancel(Order order) {
        return orderStateManager.canCancel(order);
    }

    @Override
    public void cancel(Order order) {
        orderStateManager.cancel(order);
        orderOperateHistoryService.save(orderOperateHistoryFactory.get(order, OrderOperateTypeConstants.CANCEL_ORDER));
    }

    @Override
    public Boolean canPay(Order order) {
        return orderStateManager.canPay(order);
    }

    @Override
    public void pay(Order order) {
        orderStateManager.pay(order);
        orderOperateHistoryService.save(orderOperateHistoryFactory.get(order, OrderOperateTypeConstants.PAY_ORDER));

    }

    @Override
    public void finishDelivery(Order order) {
        orderStateManager.finishDelivery(order);
        orderOperateHistoryService.save(orderOperateHistoryFactory.get(order, OrderOperateTypeConstants.PRODUCT_DELIVERY));
    }

    @Override
    public Boolean canConfirmReceipt(Order order) {
        return orderStateManager.canConfirmReceipt(order);
    }

    @Override
    public void confirmReceipt(Order order) {
        orderStateManager.confirmReceipt(order);
        orderOperateHistoryService.save(orderOperateHistoryFactory.get(order, OrderOperateTypeConstants.CONFIRM_RECEIPT));

    }
}
