package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.cqrs.command.ProductColumnSpuSaveCommand;
import com.whoiszxl.taowu.cqrs.command.ProductColumnSpuUpdateCommand;
import com.whoiszxl.taowu.cqrs.query.ProductColumnSpuQuery;
import com.whoiszxl.taowu.cqrs.response.ProductColumnSpuResponse;
import com.whoiszxl.taowu.entity.ProductColumnSpu;
import com.whoiszxl.taowu.service.ProductColumnSpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品专栏跟SPU关联表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/product-column-spu")
@RequiredArgsConstructor
public class ColumnSpuAdminController extends BaseController<ProductColumnSpuService, ProductColumnSpu, ProductColumnSpuResponse, ProductColumnSpuQuery, ProductColumnSpuSaveCommand, ProductColumnSpuUpdateCommand> {

}

