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
}

