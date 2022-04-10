package com.whoiszxl.service.impl;

import com.whoiszxl.cqrs.command.OrderPayCommand;
import com.whoiszxl.cqrs.command.OrderSubmitCommand;
import com.whoiszxl.entity.Order;
import com.whoiszxl.mapper.OrderMapper;
import com.whoiszxl.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public String orderSubmit(OrderSubmitCommand orderSubmitCommand) {
        //1. 校验订单
        OrderChecker orderChecker = this.checkOrder(orderSubmitCommand);

        return null;
    }

    /**
     * 校验订单
     * @param orderSubmitCommand 订单提交命令
     * @return
     */
    private OrderChecker checkOrder(OrderSubmitCommand orderSubmitCommand) {
        //1. 获取用户登录信息，校验用户信息

        //2. 获取购物车中选中的sku信息

        //3. 创建订单对象和订单item对象list

        //4. 创建orderChecker对象返回

    }

    @Override
    public String pay(OrderPayCommand orderPayCommand) {
        return null;
    }
}
