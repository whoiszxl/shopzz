package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.cqrs.command.BannerSaveCommand;
import com.whoiszxl.taowu.cqrs.command.BannerUpdateCommand;
import com.whoiszxl.taowu.cqrs.query.BannerQuery;
import com.whoiszxl.taowu.cqrs.response.BannerResponse;
import com.whoiszxl.taowu.entity.Banner;
import com.whoiszxl.taowu.service.BannerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 轮播表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/banner")
@Tag(name = "轮播相关接口")
@RequiredArgsConstructor
public class BannerAdminController extends BaseController<BannerService, Banner, BannerResponse, BannerQuery, BannerSaveCommand, BannerUpdateCommand> {

}

