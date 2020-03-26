package com.whoiszxl.order.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.entity.ReturnOrder;
import com.whoiszxl.order.service.ReturnOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 退款订单管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-退款订单管理控制器", tags = "ZMALL-退款订单管理控制器")
@RestController
@RequestMapping("/returnOrder")
public class ReturnOrderController {

    @Autowired
    private ReturnOrderService returnOrderService;

    @ApiOperation("查询所有的退款订单")
    @GetMapping
    public Result<List<ReturnOrder>> findAll() {
        List<ReturnOrder> list = returnOrderService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询退款订单")
    @ApiImplicitParam(value = "退款订单ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        ReturnOrder returnOrder = returnOrderService.getById(id);
        return Result.success(returnOrder);
    }

    @ApiOperation("新增退款订单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "returnOrder", value = "退款订单对象", required = true, dataType = "ReturnOrder", paramType = "body")})
    @PostMapping
    public Result returnOrderd(@RequestBody ReturnOrder returnOrder){
        boolean isSave = returnOrderService.save(returnOrder);
        return isSave ? Result.success() : Result.fail("returnOrderd fail");
    }

    @ApiOperation("修改退款订单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "returnOrder", value = "退款订单对象", required = true, dataType = "ReturnOrder", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "退款订单ID", dataType = "string",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody ReturnOrder returnOrder, @PathVariable String id){
        returnOrder.setId(id);
        boolean isUpdate = returnOrderService.updateById(returnOrder);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除退款订单数据")
    @ApiImplicitParam(value = "退款订单ID",name = "id",dataType = "string",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        boolean isDelete = returnOrderService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}