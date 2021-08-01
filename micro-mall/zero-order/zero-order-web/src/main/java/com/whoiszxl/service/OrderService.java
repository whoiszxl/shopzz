package com.whoiszxl.service;

import com.whoiszxl.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.vo.OrderConfirmVO;
import com.whoiszxl.entity.vo.OrderSubmitVO;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
public interface OrderService extends IService<Order> {

    /**
     * 获取订单确认界面的数据
     * @return 订单确认界面的数据
     */
    OrderConfirmVO getOrderConfirmData();

    /**
     * 提交订单
     * @param orderSubmitVo 订单信息
     * @return 订单ID
     */
    String submitOrder(OrderSubmitVO orderSubmitVo);

    /**
     * 通过订单ID更新订单状态
     * @param id 订单ID
     * @param orderStatus 订单状态
     * @return 是否更新成功
     */
    Boolean updateStatus(Long id, Integer orderStatus);
}
