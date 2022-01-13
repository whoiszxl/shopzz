package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.query.OrderSubmitRequest;
import com.whoiszxl.entity.vo.OrderPayVO;
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
 * @since 2022-01-09
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @SaCheckLogin
    @PostMapping("/submit")
    @ApiOperation(value = "提交订单", notes = "提交订单", response = String.class)
    public ResponseResult<String> orderSubmit(@RequestBody OrderSubmitRequest orderSubmitRequest) {
        String orderId = orderService.orderSubmit(orderSubmitRequest);
        return ResponseResult.buildSuccess(orderId);
    }


    @SaCheckLogin
    @PostMapping("/pay")
    @ApiOperation(value = "去支付", notes = "去支付", response = Boolean.class)
    public ResponseResult<String> pay(@RequestBody OrderPayVO orderPayVO) {
        return orderService.pay(orderPayVO);
    }
}

