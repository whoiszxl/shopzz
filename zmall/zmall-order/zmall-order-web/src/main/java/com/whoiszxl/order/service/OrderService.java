package com.whoiszxl.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.order.entity.Order;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2020-03-26
 */
public interface OrderService extends IService<Order> {

    /**
     * 下单操作
     * @param order 订单实体
     * @return
     */
    String addOrder(Order order);


    /**
     * 修改订单为已支付
     * @param mallOrderId
     * @param alipayOrderId
     */
    void updatePayStatusToPaid(String mallOrderId, String alipayOrderId);
}
