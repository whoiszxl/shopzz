package com.whoiszxl.search.controller;


import com.whoiszxl.search.entity.Activity;
import com.whoiszxl.search.service.ActivityService;
import com.whoiszxl.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 活动管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-活动管理控制器", tags = "ZMALL-活动管理控制器")
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @ApiOperation("查询所有的活动")
    @GetMapping
    public Result<List<Activity>> findAll() {
        List<Activity> list = activityService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询活动")
    @ApiImplicitParam(value = "活动ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Activity activity = activityService.getById(id);
        return Result.success(activity);
    }

    @ApiOperation("新增活动数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activity", value = "活动对象", required = true, dataType = "Activity", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody Activity activity){
        boolean isSave = activityService.save(activity);
        return isSave ? Result.success() : Result.fail("add fail");
    }

    @ApiOperation("修改活动数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activity", value = "活动对象", required = true, dataType = "Activity", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "活动ID", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Activity activity, @PathVariable Integer id){
        activity.setId(id);
        boolean isUpdate = activityService.updateById(activity);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除活动数据")
    @ApiImplicitParam(value = "活动ID",name = "id",dataType = "integer",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        boolean isDelete = activityService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}
