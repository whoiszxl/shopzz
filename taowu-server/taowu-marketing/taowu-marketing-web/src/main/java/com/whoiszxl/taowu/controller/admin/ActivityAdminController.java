package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.cqrs.command.ActivitySaveCommand;
import com.whoiszxl.taowu.cqrs.command.ActivityUpdateCommand;
import com.whoiszxl.taowu.cqrs.query.ActivityQuery;
import com.whoiszxl.taowu.cqrs.response.ActivityResponse;
import com.whoiszxl.taowu.entity.Activity;
import com.whoiszxl.taowu.service.ActivityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/activity")
@Tag(name = "活动相关接口")
@RequiredArgsConstructor
public class ActivityAdminController extends BaseController<ActivityService, Activity, ActivityResponse, ActivityQuery, ActivitySaveCommand, ActivityUpdateCommand> {

}

