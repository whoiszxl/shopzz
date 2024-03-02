package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.cqrs.command.BrandSaveCommand;
import com.whoiszxl.taowu.cqrs.command.BrandUpdateCommand;
import com.whoiszxl.taowu.entity.Brand;
import com.whoiszxl.taowu.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/brand")
@Tag(name = "品牌后台相关接口")
@RequiredArgsConstructor
public class BrandAdminController extends BaseController<BrandService, Brand, Brand, PageQuery, BrandSaveCommand, BrandUpdateCommand> {

}

