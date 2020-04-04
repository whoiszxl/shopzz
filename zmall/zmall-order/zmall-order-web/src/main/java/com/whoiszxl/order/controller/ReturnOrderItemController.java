package com.whoiszxl.order.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.config.TokenDecode;
import com.whoiszxl.order.entity.ReturnOrderItem;
import com.whoiszxl.order.service.ReturnOrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 退货订单详情管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-退货订单详情管理控制器", tags = "ZMALL-退货订单详情管理控制器")
@RestController
@RequestMapping("/returnReturnOrderItemItem")
public class ReturnOrderItemController {

    @Autowired
    private TokenDecode tokenDecode;

    @Autowired
    private ReturnOrderItemService returnReturnOrderItemItemService;

    @ApiOperation("查询所有的退货订单详情")
    @GetMapping
    public Result<List<ReturnOrderItem>> findAll() {
        List<ReturnOrderItem> list = returnReturnOrderItemItemService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询退货订单详情")
    @ApiImplicitParam(value = "退货订单详情ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        ReturnOrderItem returnReturnOrderItemItem = returnReturnOrderItemItemService.getById(id);
        return Result.success(returnReturnOrderItemItem);
    }

    @ApiOperation("新增退货订单详情数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "returnReturnOrderItemItem", value = "退货订单详情对象", required = true, dataType = "ReturnOrderItem", paramType = "body")})
    @PostMapping
    public Result returnReturnOrderItemItemd(@RequestBody ReturnOrderItem returnReturnOrderItemItem){
        boolean isSave = returnReturnOrderItemItemService.save(returnReturnOrderItemItem);
        return isSave ? Result.success() : Result.fail("returnReturnOrderItemItemd fail");
    }

    @ApiOperation("修改退货订单详情数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "returnReturnOrderItemItem", value = "退货订单详情对象", required = true, dataType = "ReturnOrderItem", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "退货订单详情ID", dataType = "string",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody ReturnOrderItem returnReturnOrderItemItem, @PathVariable String id){
        returnReturnOrderItemItem.setId(id);
        boolean isUpdate = returnReturnOrderItemItemService.updateById(returnReturnOrderItemItem);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除退货订单详情数据")
    @ApiImplicitParam(value = "退货订单详情ID",name = "id",dataType = "string",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        boolean isDelete = returnReturnOrderItemItemService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}
