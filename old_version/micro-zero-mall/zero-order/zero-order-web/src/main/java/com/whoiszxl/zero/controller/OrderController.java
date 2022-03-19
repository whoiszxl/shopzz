package com.whoiszxl.zero.controller;

import com.whoiszxl.zero.entity.param.SubmitOrderParam;
import com.whoiszxl.zero.entity.vo.OrderConfirmVO;
import com.whoiszxl.zero.entity.vo.OrderVO;
import com.whoiszxl.zero.service.OrderService;
import com.whoiszxl.zero.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author zhouxiaolong
 * @date 2021/4/27
 */
@Api(tags = "主页接口")
@RestController
public class OrderController {


    private OrderService orderService;


    @ApiOperation("确认订单")
    @PostMapping("/confirm")
    public Result<OrderConfirmVO> confirmOrder() {
        return Result.buildSuccess(orderService.confirmOrder());
    }

    @ApiOperation("提交订单")
    @PostMapping("/submit")
    public Result submitOrder(@RequestBody SubmitOrderParam param) {
        OrderVO orderVO = orderService.submitOrder(param);
        return Result.buildSuccess(orderVO);
    }

}
