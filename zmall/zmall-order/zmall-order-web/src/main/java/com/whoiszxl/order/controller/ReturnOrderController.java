package com.whoiszxl.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.common.constant.BooleanEnum;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.config.TokenDecode;
import com.whoiszxl.order.entity.Order;
import com.whoiszxl.order.entity.ReturnOrder;
import com.whoiszxl.order.service.OrderService;
import com.whoiszxl.order.service.ReturnOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
    private TokenDecode tokenDecode;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReturnOrderService returnOrderService;

    @ApiOperation("查询当前用户所有的退款订单")
    @GetMapping
    public Result<List<ReturnOrder>> findAll() {
        String username = tokenDecode.getUsername();
        QueryWrapper<ReturnOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", username);
        List<ReturnOrder> list = returnOrderService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation("根据ID查询退款订单")
    @ApiImplicitParam(value = "退款订单ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        String username = tokenDecode.getUsername();
        QueryWrapper<ReturnOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", username);
        queryWrapper.eq("id", id);
        ReturnOrder returnOrder = returnOrderService.getOne(queryWrapper);
        return Result.success(returnOrder);
    }

    @ApiOperation("新增退款订单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "returnOrder", value = "退款订单对象", required = true, dataType = "ReturnOrder", paramType = "body")})
    @PostMapping
    public Result addReturnOrder(@RequestBody ReturnOrder returnOrder){
        //判断订单是否是当前用户的
        String username = tokenDecode.getUsername();
        Order order = orderService.findByUsernameAndOrderId(username, returnOrder.getOrderId());
        if(order == null) {
            return Result.fail("订单号错误");
        }

        returnOrder.setUserId(username);
        returnOrder.setUserAccount(username);
        returnOrder.setStatus(BooleanEnum.IS_FALSE.getBool());
        boolean isSave = returnOrderService.save(returnOrder);
        return isSave ? Result.success() : Result.fail("returnOrderd fail");
    }

    @ApiOperation("修改退款订单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "returnOrder", value = "退款订单对象", required = true, dataType = "ReturnOrder", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "退款订单ID", dataType = "string",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody ReturnOrder returnOrder, @PathVariable String id){
        //判断订单是否是当前用户的
        String username = tokenDecode.getUsername();
        Order order = orderService.findByUsernameAndOrderId(username, returnOrder.getOrderId());
        if(order == null) {
            return Result.fail("订单号错误");
        }
        returnOrder.setId(id);
        boolean isUpdate = returnOrderService.updateById(returnOrder);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除退款订单数据")
    @ApiImplicitParam(value = "退款订单ID",name = "id",dataType = "string",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //判断退款订单是否是当前用户的
        String username = tokenDecode.getUsername();
        ReturnOrder returnOrder = returnOrderService.getById(id);
        if(!StringUtils.equals(username, returnOrder.getUserAccount())) {
            return Result.fail("退款申请ID错误");
        }
        boolean isDelete = returnOrderService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}