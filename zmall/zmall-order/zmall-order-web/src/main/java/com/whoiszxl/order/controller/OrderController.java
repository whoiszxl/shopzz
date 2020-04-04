package com.whoiszxl.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.common.constant.BooleanEnum;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.config.TokenDecode;
import com.whoiszxl.order.entity.Order;
import com.whoiszxl.order.entity.OrderItem;
import com.whoiszxl.order.service.OrderItemService;
import com.whoiszxl.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 订单管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-订单管理控制器", tags = "ZMALL-订单管理控制器")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private TokenDecode tokenDecode;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @ApiOperation("查询当前用户的所有订单")
    @GetMapping
    public Result<List<Order>> findAll() {
        String username = tokenDecode.getUsername();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<Order> list = orderService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation("根据ID查询当前用户的订单")
    @ApiImplicitParam(value = "订单ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result<Order> findById(@PathVariable String id){
        String username = tokenDecode.getUsername();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("id", id);
        Order order = orderService.getOne(queryWrapper);
        return Result.success(order);
    }

    @ApiOperation("新增订单数据")
    @ApiImplicitParam(name = "order", value = "订单对象", required = true, dataType = "Order", paramType = "body")
    @PostMapping
    public Result<String> add(@RequestBody Order order){
        String username = tokenDecode.getUsername();
        order.setUsername(username);
        String orderId = orderService.addOrder(order);
        return StringUtils.isNotEmpty(orderId) ? Result.success(orderId) : Result.fail("add order fail");
    }


    @ApiOperation("根据ID删除订单数据")
    @ApiImplicitParam(value = "订单ID",name = "id",dataType = "string",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //判断订单是否是当前用户的
        Order order = orderService.getById(id);
        String username = tokenDecode.getUsername();
        if(!order.getUsername().equals(username)) {
            return Result.fail("订单号错误");
        }

        //软删除,还需关联删除order_item
        order.setIsDelete(BooleanEnum.IS_TRUE.getBool());
        order.setUpdateTime(LocalDateTime.now());
        boolean isDelete = orderService.updateById(order);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }

    @ApiOperation("根据订单号查询订单详情")
    @ApiImplicitParam(value = "订单号",name = "orderId",dataType = "string",paramType = "path")
    @GetMapping("/{orderId}")
    public Result findByOrderId(@PathVariable String orderId){
        //判断订单是否是当前用户的
        String username = tokenDecode.getUsername();
        Order order = orderService.findByUsernameAndOrderId(username, orderId);
        if(order == null) {
            return Result.fail("订单号错误");
        }

        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        List<OrderItem> list = orderItemService.list(queryWrapper);
        return Result.success(list);
    }
}


