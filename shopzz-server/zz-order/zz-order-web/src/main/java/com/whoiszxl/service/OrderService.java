package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.cqrs.command.OrderPayCommand;
import com.whoiszxl.cqrs.command.OrderSubmitCommand;
import com.whoiszxl.entity.Order;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
public interface OrderService extends IService<Order> {

    /**
     * 提交订单
     * @param orderSubmitCommand 订单提交命令
     * @return 订单ID
     */
    Long orderSubmit(OrderSubmitCommand orderSubmitCommand);

    /**
     * 去支付订单
     * @param orderPayCommand 订单支付命令
     * @return
     */
    String pay(OrderPayCommand orderPayCommand);


    /**
     * 通过订单ID更新订单状态
     * @param id 订单ID
     * @param orderStatus 订单状态
     * @return 是否更新成功
     */
    Boolean updateStatus(Long id, Integer orderStatus);
}
