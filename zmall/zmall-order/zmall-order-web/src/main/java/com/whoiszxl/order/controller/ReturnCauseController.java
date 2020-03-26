package com.whoiszxl.returnCause.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.order.entity.ReturnCause;
import com.whoiszxl.order.service.ReturnCauseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 退货原因管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-退货原因管理控制器", tags = "ZMALL-退货原因管理控制器")
@RestController
@RequestMapping("/returnCause")
public class ReturnCauseController {

    @Autowired
    private ReturnCauseService returnCauseService;

    @ApiOperation("查询所有的退货原因")
    @GetMapping
    public Result<List<ReturnCause>> findAll() {
        List<ReturnCause> list = returnCauseService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询退货原因")
    @ApiImplicitParam(value = "退货原因ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        ReturnCause returnCause = returnCauseService.getById(id);
        return Result.success(returnCause);
    }

    @ApiOperation("新增退货原因数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "returnCause", value = "退货原因对象", required = true, dataType = "ReturnCause", paramType = "body")})
    @PostMapping
    public Result returnCaused(@RequestBody ReturnCause returnCause){
        boolean isSave = returnCauseService.save(returnCause);
        return isSave ? Result.success() : Result.fail("returnCaused fail");
    }

    @ApiOperation("修改退货原因数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "returnCause", value = "退货原因对象", required = true, dataType = "ReturnCause", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "退货原因ID", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody ReturnCause returnCause, @PathVariable Integer id){
        returnCause.setId(id);
        boolean isUpdate = returnCauseService.updateById(returnCause);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除退货原因数据")
    @ApiImplicitParam(value = "退货原因ID",name = "id",dataType = "integer",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        boolean isDelete = returnCauseService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}
