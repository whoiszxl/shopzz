package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.command.SkuSaveCommand;
import com.whoiszxl.taowu.cqrs.query.SkuQuery;
import com.whoiszxl.taowu.entity.Sku;
import com.whoiszxl.taowu.service.SkuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * sku信息表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/sku")
@Tag(name = "SKU后台相关接口")
@RequiredArgsConstructor
public class SkuAdminController extends BaseController<SkuService, Sku, Sku, SkuQuery, SkuSaveCommand, SkuSaveCommand> {

}

