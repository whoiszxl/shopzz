package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.ActivitySaveCommand;
import com.whoiszxl.cqrs.command.ActivityUpdateCommand;
import com.whoiszxl.cqrs.query.ActivityQuery;
import com.whoiszxl.cqrs.response.ActivityResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Activity;
import com.whoiszxl.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 促销活动表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/activity")
@Api(tags = "活动相关接口")
public class ActivityAdminController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取活动列表", notes = "分页获取活动列表", response = ActivityResponse.class)
    public ResponseResult<IPage<ActivityResponse>> list(@RequestBody ActivityQuery query) {
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getName())) {
            wrapper.like(Activity::getName, "%" + query.getName() + "%");
        }
        if(query.getStatus() != null) {
            wrapper.eq(Activity::getStatus, query.getStatus());
        }
        IPage<Activity> result = activityService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<ActivityResponse> finalResult = result.convert(e -> dozerUtils.map(e, ActivityResponse.class));
        return ResponseResult.buildSuccess(finalResult);

    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取活动", notes = "通过主键ID获取活动", response = ActivityResponse.class)
    public ResponseResult<ActivityResponse> getSupplierById(@PathVariable Long id) {
        Activity banner = activityService.getById(id);
        return banner == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(banner, ActivityResponse.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增活动", notes = "新增活动", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody ActivitySaveCommand bannerSaveCommand) {
        Activity banner = dozerUtils.map(bannerSaveCommand, Activity.class);
        boolean saveFlag = activityService.save(banner);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新活动", notes = "更新活动", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody ActivityUpdateCommand bannerSaveCommand) {
        Activity banner = dozerUtils.map(bannerSaveCommand, Activity.class);
        boolean updateFlag = activityService.updateById(banner);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除活动", notes = "删除活动", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = activityService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }


}

