package com.whoiszxl.search.controller;


import com.whoiszxl.search.entity.Ad;
import com.whoiszxl.search.service.AdService;
import com.whoiszxl.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 广告管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-广告管理控制器", tags = "ZMALL-广告管理控制器")
@RestController
@RequestMapping("/ad")
public class AdController {

    @Autowired
    private AdService adService;

    @ApiOperation("查询所有的广告")
    @GetMapping
    public Result<List<Ad>> findAll() {
        List<Ad> list = adService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询广告")
    @ApiImplicitParam(value = "广告ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Ad ad = adService.getById(id);
        return Result.success(ad);
    }

    @ApiOperation("新增广告数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ad", value = "广告对象", required = true, dataType = "Ad", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody Ad ad){
        boolean isSave = adService.save(ad);
        return isSave ? Result.success() : Result.fail("add fail");
    }

    @ApiOperation("修改广告数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ad", value = "广告对象", required = true, dataType = "Ad", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "广告ID", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Ad ad, @PathVariable Integer id){
        ad.setId(id);
        boolean isUpdate = adService.updateById(ad);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除广告数据")
    @ApiImplicitParam(value = "广告ID",name = "id",dataType = "integer",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        boolean isDelete = adService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}
