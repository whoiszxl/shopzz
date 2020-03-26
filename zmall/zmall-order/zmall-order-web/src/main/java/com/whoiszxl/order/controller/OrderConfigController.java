package com.whoiszxl.order.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.entity.OrderConfig;
import com.whoiszxl.order.service.OrderConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 订单配置管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-订单配置管理控制器", tags = "ZMALL-订单配置管理控制器")
@RestController
@RequestMapping("/orderConfig")
public class OrderConfigController {

    @Autowired
    private OrderConfigService orderConfigService;

    @ApiOperation("查询所有的订单配置")
    @GetMapping
    public Result<List<OrderConfig>> findAll() {
        List<OrderConfig> list = orderConfigService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询订单配置")
    @ApiImplicitParam(value = "订单配置ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        OrderConfig orderConfig = orderConfigService.getById(id);
        return Result.success(orderConfig);
    }

    @ApiOperation("新增订单配置数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderConfig", value = "订单配置对象", required = true, dataType = "OrderConfig", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody OrderConfig orderConfig){
        boolean isSave = orderConfigService.save(orderConfig);
        return isSave ? Result.success() : Result.fail("orderConfigd fail");
    }

    @ApiOperation("修改订单配置数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderConfig", value = "订单配置对象", required = true, dataType = "OrderConfig", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "订单配置ID", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody OrderConfig orderConfig, @PathVariable Integer id){
        orderConfig.setId(id);
        boolean isUpdate = orderConfigService.updateById(orderConfig);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除订单配置数据")
    @ApiImplicitParam(value = "订单配置ID",name = "id",dataType = "integer",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        boolean isDelete = orderConfigService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}

