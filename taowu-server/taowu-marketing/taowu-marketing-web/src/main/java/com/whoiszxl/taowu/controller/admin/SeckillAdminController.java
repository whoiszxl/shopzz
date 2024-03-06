package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.cqrs.command.SeckillSaveCommand;
import com.whoiszxl.taowu.cqrs.command.SeckillUpdateCommand;
import com.whoiszxl.taowu.cqrs.query.SeckillQuery;
import com.whoiszxl.taowu.cqrs.response.SeckillResponse;
import com.whoiszxl.taowu.entity.Seckill;
import com.whoiszxl.taowu.service.SeckillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 秒杀表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/seckill")
@Tag(name = "秒杀活动相关接口")
@RequiredArgsConstructor
public class SeckillAdminController extends BaseController<SeckillService, Seckill, SeckillResponse, SeckillQuery, SeckillSaveCommand, SeckillUpdateCommand> {

}

