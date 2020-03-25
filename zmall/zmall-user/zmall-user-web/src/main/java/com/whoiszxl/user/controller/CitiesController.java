package com.whoiszxl.user.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.user.entity.Cities;
import com.whoiszxl.user.service.CitiesService;
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
 * 行政区域地州市信息表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
@Api(value = "ZMALL-城市管理控制器", tags = "ZMALL-城市管理控制器")
@RestController
@RequestMapping("/cities")
public class CitiesController {

    @Autowired
    private CitiesService citiesService;

    @ApiOperation("查询所有的城市")
    @GetMapping
    public Result<List<Cities>> findAll() {
        List<Cities> list = citiesService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询城市")
    @ApiImplicitParam(value = "城市ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Cities cities = citiesService.getById(id);
        return Result.success(cities);
    }

    @ApiOperation("新增城市数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cities", value = "城市对象", required = true, dataType = "Cities", paramType = "body")})
    @PostMapping
    public Result citiesd(@RequestBody Cities cities){
        boolean isSave = citiesService.save(cities);
        return isSave ? Result.success() : Result.fail("citiesd fail");
    }

    @ApiOperation("修改城市数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cities", value = "城市对象", required = true, dataType = "Cities", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "城市ID", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Cities cities, @PathVariable String id){
        cities.setCityid(id);
        boolean isUpdate = citiesService.updateById(cities);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除城市数据")
    @ApiImplicitParam(value = "城市ID",name = "id",dataType = "integer",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        boolean isDelete = citiesService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}
