package com.whoiszxl.taowu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.command.OrderPayCommand;
import com.whoiszxl.taowu.cqrs.command.OrderSubmitCommand;
import com.whoiszxl.taowu.cqrs.query.OrderListQuery;
import com.whoiszxl.taowu.cqrs.response.OrderResponse;
import com.whoiszxl.taowu.dto.OrderInfoDTO;
import com.whoiszxl.taowu.entity.Order;
import com.whoiszxl.taowu.entity.PayInfoDc;

import java.util.List;

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
    ResponseResult<PayInfoDc> pay(OrderPayCommand orderPayCommand);


    /**
     * 通过订单ID更新订单状态
     * @param id 订单ID
     * @param orderStatus 订单状态
     * @return 是否更新成功
     */
    Boolean updateStatus(Long id, Integer orderStatus);


    /**
     * 获取订单的详细信息
     * @param orderId 订单ID
     * @return 订单详细信息
     */
    OrderInfoDTO getOrderInfo(Long orderId);


    /**
     * 获取订单列表
     * @param orderListQuery 订单查询参数
     * @return 订单列表
     */
    List<OrderResponse> orderList(OrderListQuery orderListQuery);

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    OrderResponse orderDetail(Long orderId);
}
