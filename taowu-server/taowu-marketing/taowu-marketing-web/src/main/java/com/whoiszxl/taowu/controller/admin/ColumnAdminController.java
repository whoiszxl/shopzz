package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.cqrs.command.ProductColumnSaveCommand;
import com.whoiszxl.taowu.cqrs.command.ProductColumnUpdateCommand;
import com.whoiszxl.taowu.cqrs.query.ProductColumnQuery;
import com.whoiszxl.taowu.cqrs.response.ProductColumnResponse;
import com.whoiszxl.taowu.entity.ProductColumn;
import com.whoiszxl.taowu.service.ProductColumnService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品专栏表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/column")
@Tag(name = "专栏相关接口")
@RequiredArgsConstructor
public class ColumnAdminController extends BaseController<ProductColumnService, ProductColumn, ProductColumnResponse, ProductColumnQuery, ProductColumnSaveCommand, ProductColumnUpdateCommand> {

}

