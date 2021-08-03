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
        return ResponseResult.buildByFlag(updateFlag);
    }
}
