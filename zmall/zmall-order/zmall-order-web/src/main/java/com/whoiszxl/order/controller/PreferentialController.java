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

    @ApiOperation("新增订单优惠数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "preferential", value = "订单优惠对象", required = true, dataType = "Preferential", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody Preferential preferential){
        boolean isSave = preferentialService.save(preferential);
        return isSave ? Result.success() : Result.fail("preferentiald fail");
    }

    @ApiOperation("修改订单优惠数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "preferential", value = "订单优惠对象", required = true, dataType = "Preferential", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "订单优惠ID", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Preferential preferential, @PathVariable Integer id){
        preferential.setId(id);
        boolean isUpdate = preferentialService.updateById(preferential);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除订单优惠数据")
    @ApiImplicitParam(value = "订单优惠ID",name = "id",dataType = "integer",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        boolean isDelete = preferentialService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}
