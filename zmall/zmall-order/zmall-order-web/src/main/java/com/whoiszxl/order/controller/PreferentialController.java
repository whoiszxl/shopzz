package com.whoiszxl.order.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.entity.Preferential;
import com.whoiszxl.order.service.PreferentialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 订单优惠管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-订单优惠管理控制器", tags = "ZMALL-订单优惠管理控制器")
@RestController
@RequestMapping("/preferential")
public class PreferentialController {

    @Autowired
    private PreferentialService preferentialService;

    @ApiOperation("查询所有的订单优惠")
    @GetMapping
    public Result<List<Preferential>> findAll() {
        List<Preferential> list = preferentialService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询订单优惠")
    @ApiImplicitParam(value = "订单优惠ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        Preferential preferential = preferentialService.getById(id);
        return Result.success(preferential);
    }
}
