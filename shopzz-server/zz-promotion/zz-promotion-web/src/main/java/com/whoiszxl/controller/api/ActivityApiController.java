package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.ActivitySaveCommand;
import com.whoiszxl.cqrs.command.ActivityUpdateCommand;
import com.whoiszxl.cqrs.query.ActivityApiQuery;
import com.whoiszxl.cqrs.query.ActivityQuery;
import com.whoiszxl.cqrs.query.ProductColumnApiQuery;
import com.whoiszxl.cqrs.response.ActivityApiResponse;
import com.whoiszxl.cqrs.response.ActivityResponse;
import com.whoiszxl.cqrs.response.ColumnDetailApiResponse;
import com.whoiszxl.cqrs.response.ProductColumnResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Activity;
import com.whoiszxl.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api/activity")
@Api(tags = "C端:活动相关接口")
public class ActivityApiController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private DozerUtils dozerUtils;


    @SaCheckLogin
    @PostMapping("/detail")
    @ApiOperation(value = "通过专栏ID获取专栏详情", notes = "通过专栏ID获取专栏详情", response = ActivityApiResponse.class)
    public ResponseResult<ActivityApiResponse> detail(@RequestBody @Validated ActivityApiQuery query) {
        ActivityApiResponse response = activityService.detail(query.getId());
        return ResponseResult.buildSuccess(response);

    }

}

