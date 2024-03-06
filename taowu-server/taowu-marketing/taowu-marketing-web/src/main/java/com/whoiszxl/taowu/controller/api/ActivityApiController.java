package com.whoiszxl.taowu.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.query.ActivityApiQuery;
import com.whoiszxl.taowu.cqrs.response.ActivityApiResponse;
import com.whoiszxl.taowu.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Tag(name = "C端:活动相关接口")
@RequiredArgsConstructor
public class ActivityApiController {

    private final ActivityService activityService;

    @SaCheckLogin
    @PostMapping("/detail")
    @Operation(summary = "通过专栏ID获取专栏详情", description = "通过专栏ID获取专栏详情")
    public ResponseResult<ActivityApiResponse> detail(@RequestBody @Validated ActivityApiQuery query) {
        ActivityApiResponse response = activityService.detail(query.getId());
        return ResponseResult.buildSuccess(response);

    }

}

