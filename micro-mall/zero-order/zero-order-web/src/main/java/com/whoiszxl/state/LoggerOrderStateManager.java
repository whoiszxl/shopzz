package com.whoiszxl.state;


import com.whoisxl.constants.OrderOperateTypeConstants;
import com.whoiszxl.entity.Order;
import com.whoiszxl.factory.OrderOperateHistoryFactory;
import com.whoiszxl.service.OrderOperateHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 带操作日志记录功能的订单状态管理器
 */
@Component
public class LoggerOrderStateManager implements OrderStateManager {

    @Autowired
    private OrderStateManagerImpl orderStateManager;

    @Autowired
    private OrderOperateHistoryService orderOperateHistoryService;

    @Autowired
    private OrderOperateHistoryFactory orderOperateHistoryFactory;

    @Override
    public void create(Order order) {
        orderStateManager.create(order);
        orderOperateHistoryService.save(orderOperateHistoryFactory.get(order, OrderOperateTypeConstants.CREATE_ORDER));
    }
}
