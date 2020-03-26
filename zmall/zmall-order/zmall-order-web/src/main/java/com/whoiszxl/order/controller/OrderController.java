package com.whoiszxl.order.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.entity.Order;
import com.whoiszxl.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private OrderService orderService;

    @ApiOperation("查询所有的订单")
    @GetMapping
    public Result<List<Order>> findAll() {
        List<Order> list = orderService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询订单")
    @ApiImplicitParam(value = "订单ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        Order order = orderService.getById(id);
        return Result.success(order);
    }

    @ApiOperation("新增订单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "订单对象", required = true, dataType = "Order", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody Order order){
        boolean isSave = orderService.save(order);
        return isSave ? Result.success() : Result.fail("orderd fail");
    }

    @ApiOperation("修改订单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order", value = "订单对象", required = true, dataType = "Order", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "订单ID", dataType = "string",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Order order, @PathVariable String id){
        order.setId(id);
        boolean isUpdate = orderService.updateById(order);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除订单数据")
    @ApiImplicitParam(value = "订单ID",name = "id",dataType = "string",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        boolean isDelete = orderService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}


