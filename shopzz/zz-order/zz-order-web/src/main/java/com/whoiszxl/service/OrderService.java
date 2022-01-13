package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.query.OrderSubmitRequest;
import com.whoiszxl.entity.vo.OrderPayVO;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-01-09
 */
public interface OrderService extends IService<Order> {

    /**
     * 提交订单
     * @param orderSubmitRequest 订单信息
     * @return 订单ID
     */
    String orderSubmit(OrderSubmitRequest orderSubmitRequest);


    /**
     * 通过订单ID更新订单状态
     * @param id 订单ID
     * @param orderStatus 订单状态
     * @return 是否更新成功
     */
    Boolean updateStatus(Long id, Integer orderStatus);

    /**
     * 支付订单
     * @param orderPayVO 订单支付参数
     * @return
     */
    ResponseResult pay(OrderPayVO orderPayVO);

}
