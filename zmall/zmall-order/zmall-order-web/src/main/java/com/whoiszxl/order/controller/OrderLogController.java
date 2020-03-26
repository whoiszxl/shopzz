package com.whoiszxl.order.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.entity.OrderLog;
import com.whoiszxl.order.service.OrderLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 订单日志管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-订单日志管理控制器", tags = "ZMALL-订单日志管理控制器")
@RestController
@RequestMapping("/orderLog")
public class OrderLogController {

    @Autowired
    private OrderLogService orderLogService;

    @ApiOperation("查询所有的订单日志")
    @GetMapping
    public Result<List<OrderLog>> findAll() {
        List<OrderLog> list = orderLogService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询订单日志")
    @ApiImplicitParam(value = "订单日志ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        OrderLog orderLog = orderLogService.getById(id);
        return Result.success(orderLog);
    }

    @ApiOperation("新增订单日志数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderLog", value = "订单日志对象", required = true, dataType = "OrderLog", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody OrderLog orderLog){
        boolean isSave = orderLogService.save(orderLog);
        return isSave ? Result.success() : Result.fail("orderLogd fail");
    }

    @ApiOperation("修改订单日志数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderLog", value = "订单日志对象", required = true, dataType = "OrderLog", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "订单日志ID", dataType = "string",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody OrderLog orderLog, @PathVariable String id){
        orderLog.setId(id);
        boolean isUpdate = orderLogService.updateById(orderLog);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除订单日志数据")
    @ApiImplicitParam(value = "订单日志ID",name = "id",dataType = "string",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        boolean isDelete = orderLogService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}