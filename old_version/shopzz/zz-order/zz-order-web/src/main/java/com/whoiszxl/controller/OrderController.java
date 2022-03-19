package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.OrderItem;
import com.whoiszxl.entity.query.OrderListQuery;
import com.whoiszxl.entity.query.OrderSubmitRequest;
import com.whoiszxl.entity.vo.OrderItemVO;
import com.whoiszxl.entity.vo.OrderPayVO;
import com.whoiszxl.entity.vo.OrderVO;
import com.whoiszxl.service.OrderItemService;
import com.whoiszxl.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private OrderItemService orderItemService;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "获取当前用户的订单列表", notes = "获取当前用户的订单列表", response = String.class)
    public ResponseResult<IPage<OrderVO>> orderList(@RequestBody OrderListQuery query) {
        long memberId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getMemberId, memberId);

        if(query.getOrderStatus() != 0) {
            queryWrapper.eq(Order::getOrderStatus, query.getOrderStatus());
        }

        IPage<Order> orderList = orderService.page(new Page<>(query.getPage(), query.getSize()), queryWrapper);
        IPage<OrderVO> orderVOList = orderList.convert(order -> {
            OrderVO orderVO = dozerUtils.map(order, OrderVO.class);

            //获取orderItem
            List<OrderItem> orderItemList = orderItemService.list(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderId, orderVO.getId()));
            List<OrderItemVO> orderItemVOS = dozerUtils.mapList(orderItemList, OrderItemVO.class);

            orderVO.setOrderItemVOList(orderItemVOS);
            return orderVO;
        });
        return ResponseResult.buildSuccess(orderVOList);
    }

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