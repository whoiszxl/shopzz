package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.OrderPayCommand;
import com.whoiszxl.cqrs.command.OrderSubmitCommand;
import com.whoiszxl.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "C端:订单相关接口")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @SaCheckLogin
    @PostMapping("/submit")
    @ApiOperation(value = "提交订单", notes = "返回订单ID", response = Long.class)
    public ResponseResult<Long> orderSubmit(@RequestBody OrderSubmitCommand orderSubmitCommand) {
        Long orderId = orderService.orderSubmit(orderSubmitCommand);
        return ResponseResult.buildSuccess(orderId);
    }

    @SaCheckLogin
    @PostMapping("/pay")
    @ApiOperation(value = "去支付", notes = "去支付", response = Boolean.class)
    public ResponseResult<String> pay(@RequestBody OrderPayCommand orderPayCommand) {
        return orderService.pay(orderPayCommand);
    }
}

