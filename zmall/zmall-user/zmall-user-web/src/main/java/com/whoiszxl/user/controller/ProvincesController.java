package com.whoiszxl.user.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.user.entity.Provinces;
import com.whoiszxl.user.service.ProvincesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 省份信息表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
@Api(value = "ZMALL-省份管理控制器", tags = "ZMALL-省份管理控制器")
@RestController
@RequestMapping("/ad")
public class ProvincesController {

    @Autowired
    private ProvincesService adService;

    @ApiOperation("查询所有的省份")
    @GetMapping
    public Result<List<Provinces>> findAll() {
        List<Provinces> list = adService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询省份")
    @ApiImplicitParam(value = "省份ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Provinces ad = adService.getById(id);
        return Result.success(ad);
    }
}
