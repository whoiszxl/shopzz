package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.cqrs.query.SeckillOrderQuery;
import com.whoiszxl.taowu.cqrs.response.SeckillOrderResponse;
import com.whoiszxl.taowu.entity.SeckillOrder;
import com.whoiszxl.taowu.service.SeckillOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 秒杀订单表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/seckill-order")
@RequiredArgsConstructor
public class SeckillOrderAdminController extends BaseController<SeckillOrderService, SeckillOrder, SeckillOrderResponse, SeckillOrderQuery, SeckillOrder, SeckillOrder> {

}

