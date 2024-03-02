package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.core.entity.ResponseResult;
import com.whoiszxl.cqrs.command.OrderPayCommand;
import com.whoiszxl.cqrs.command.OrderSubmitCommand;
import com.whoiszxl.cqrs.query.OrderListQuery;
import com.whoiszxl.cqrs.response.OrderResponse;
import com.whoiszxl.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "C端:订单相关接口")
public class OrderApiController {

    private final OrderService orderService;

    @SaCheckLogin
    @PostMapping("/submit")
    @Operation(summary = "提交订单", description = "返回订单ID")
    public ResponseResult<Long> orderSubmit(@RequestBody OrderSubmitCommand orderSubmitCommand) {
        Long orderId = orderService.orderSubmit(orderSubmitCommand);
        return ResponseResult.buildSuccess(orderId);
    }

    @SaCheckLogin
    @PostMapping("/pay")
    @Operation(summary = "去支付", description = "去支付")
    public ResponseResult<String> pay(@RequestBody OrderPayCommand orderPayCommand) {
        return orderService.pay(orderPayCommand);
    }

    @SaCheckLogin
    @PostMapping("/list")
    @Operation(summary = "获取当前用户的订单列表", description = "获取当前用户的订单列表")
    public ResponseResult<List<OrderResponse>> orderList(@RequestBody OrderListQuery orderListQuery) {
        List<OrderResponse> orderResponseList = orderService.orderList(orderListQuery);
        return ResponseResult.buildSuccess(orderResponseList);
    }

    @SaCheckLogin
    @PostMapping("/detail/{orderId}")
    @Operation(summary = "获取订单详情", description = "获取订单详情")
    public ResponseResult<OrderResponse> orderDetail(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.orderDetail(orderId);
        return ResponseResult.buildSuccess(orderResponse);
    }

}

