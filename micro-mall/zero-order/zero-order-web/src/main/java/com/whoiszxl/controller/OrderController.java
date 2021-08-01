package com.whoiszxl.controller;


import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.vo.OrderConfirmVO;
import com.whoiszxl.entity.vo.OrderSubmitVO;
import com.whoiszxl.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/confirm")
    @ApiOperation(value = "获取确认订单界面的数据", notes = "收货地址，SKU详情，优惠金额，结算金额，运费等", response = OrderConfirmVO.class)
    public ResponseResult<OrderConfirmVO> getOrderConfirmData() {
        OrderConfirmVO orderConfirmVO = orderService.getOrderConfirmData();
        return ResponseResult.buildSuccess(orderConfirmVO);
    }

    @GetMapping("/submit")
    @ApiOperation(value = "提交订单", notes = "提交订单", response = Boolean.class)
    public ResponseResult<String> submit(@RequestBody OrderSubmitVO orderSubmitVo) {
        String orderId = orderService.submitOrder(orderSubmitVo);
        return ResponseResult.buildSuccess(orderId);
    }



}

