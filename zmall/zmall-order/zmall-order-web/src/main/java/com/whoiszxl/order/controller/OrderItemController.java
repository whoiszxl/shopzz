package com.whoiszxl.order.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.entity.OrderItem;
import com.whoiszxl.order.service.OrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 购物车详情管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-购物车详情管理控制器", tags = "ZMALL-购物车详情管理控制器")
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @ApiOperation("查询所有的购物车详情")
    @GetMapping
    public Result<List<OrderItem>> findAll() {
        List<OrderItem> list = orderItemService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询购物车详情")
    @ApiImplicitParam(value = "购物车详情ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        OrderItem orderItem = orderItemService.getById(id);
        return Result.success(orderItem);
    }

    @ApiOperation("新增购物车详情数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderItem", value = "购物车详情对象", required = true, dataType = "OrderItem", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody OrderItem orderItem){
        boolean isSave = orderItemService.save(orderItem);
        return isSave ? Result.success() : Result.fail("orderItemd fail");
    }

    @ApiOperation("修改购物车详情数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderItem", value = "购物车详情对象", required = true, dataType = "OrderItem", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "购物车详情ID", dataType = "string",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody OrderItem orderItem, @PathVariable String id){
        orderItem.setId(id);
        boolean isUpdate = orderItemService.updateById(orderItem);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除购物车详情数据")
    @ApiImplicitParam(value = "购物车详情ID",name = "id",dataType = "string",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        boolean isDelete = orderItemService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}