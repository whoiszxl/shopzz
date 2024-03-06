package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.cqrs.command.SeckillItemSaveCommand;
import com.whoiszxl.taowu.cqrs.command.SeckillItemUpdateCommand;
import com.whoiszxl.taowu.cqrs.query.SeckillItemQuery;
import com.whoiszxl.taowu.cqrs.response.SeckillItemResponse;
import com.whoiszxl.taowu.entity.SeckillItem;
import com.whoiszxl.taowu.service.SeckillItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 秒杀item表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/seckill/item")
@Tag(name = "秒杀秒杀商品item相关接口")
@RequiredArgsConstructor
public class SeckillItemAdminController extends BaseController<SeckillItemService, SeckillItem, SeckillItemResponse, SeckillItemQuery, SeckillItemSaveCommand, SeckillItemUpdateCommand> {
    
}

