package com.whoiszxl.client;

import com.whoiszxl.constants.OrderStatusConstants;
import com.whoiszxl.feign.OrderFeignClient;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Order;
import com.whoiszxl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单feign client
 *
 * @author whoiszxl
 * @date 2021/8/3
 */
@RestController
public class OrderFeignClientImpl implements OrderFeignClient {

    @Autowired
    private OrderService orderService;

    @Override
    public ResponseResult<Boolean> notifyDcPaySuccess(List<Long> orderIds) {
        //1. 更新订单到发货状态
        List<Order> orderList = orderIds.stream().map(orderId -> {
            Order order = new Order();
            order.setId(orderId);
            order.setOrderStatus(OrderStatusConstants.WAIT_FOR_DELIVERY);
            order.setUpdatedBy("admin");
            return order;
        }).collect(Collectors.toList());
        boolean updateFlag = orderService.updateBatchById(orderList);

        //2. 清空购物车

        //3. 通知库存中心更新库存

        //4. 更新调度中心库存

        //5. 通知WMS新增出库单

        //6. 更新会员中心等级与积分


        return ResponseResult.buildByFlag(updateFlag);
    }
}
