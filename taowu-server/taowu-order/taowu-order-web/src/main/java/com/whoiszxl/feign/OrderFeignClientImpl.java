package com.whoiszxl.feign;

import com.whoiszxl.constants.OrderStatusConstants;
import com.whoiszxl.core.entity.ResponseResult;
import com.whoiszxl.dto.OrderInfoDTO;
import com.whoiszxl.entity.Order;
import com.whoiszxl.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单feign client
 *
 * @author whoiszxl
 * @date 2021/8/3
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderFeignClientImpl implements OrderFeignClient {

    private final OrderService orderService;

    private final ProductFeignClient productFeignClient;

    private final MemberFeignClient memberFeignClient;

    @Override
    public ResponseResult<Boolean> notifyDcPaySuccess(List<Long> orderIds) {
        //1. 更新订单到发货状态
        List<Order> orderList = orderIds.stream().map(orderId -> {
            Order order = new Order();
            order.setId(orderId);
            order.setOrderStatus(OrderStatusConstants.WAIT_FOR_DELIVERY);
            order.setUpdatedBy("system");
            return order;
        }).collect(Collectors.toList());
        boolean updateFlag = orderService.updateBatchById(orderList);

        for (Long orderId : orderIds) {
            OrderInfoDTO orderInfo = orderService.getOrderInfo(orderId);

            //通知商品中心更新库存
            ResponseResult result = productFeignClient.paySuccessUpdateStock(orderInfo);
            if(!result.isOk()) {
                log.info("库存更新失败");
            }

            //TODO 通知WMS订单支付成功，更新WMS中心库存，WMS新增出库单

            //TODO 更新会员中心等级与积分
        }

        return ResponseResult.buildByFlag(updateFlag);
    }
}
