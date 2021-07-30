package com.whoiszxl.service;

import com.whoiszxl.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.vo.OrderConfirmVO;

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

}
