package com.whoiszxl.user.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.user.entity.Areas;
import com.whoiszxl.user.service.AreasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 行政区域县区信息表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
@Api(value = "ZMALL-地区管理控制器", tags = "ZMALL-地区管理控制器")
@RestController
@RequestMapping("/areas")
public class AreasController {

    @Autowired
    private AreasService areasService;

    @ApiOperation("查询所有的地区")
    @GetMapping
    public Result<List<Areas>> findAll() {
        List<Areas> list = areasService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询地区")
    @ApiImplicitParam(value = "地区ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        Areas areas = areasService.getById(id);
        return Result.success(areas);
    }
}
