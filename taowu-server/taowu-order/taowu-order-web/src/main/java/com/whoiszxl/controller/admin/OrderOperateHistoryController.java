package com.whoiszxl.controller.admin;


import com.whoiszxl.core.base.BaseController;
import com.whoiszxl.cqrs.admin.query.OrderOperateHistoryQuery;
import com.whoiszxl.cqrs.admin.query.OrderQuery;
import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.OrderOperateHistory;
import com.whoiszxl.service.OrderItemService;
import com.whoiszxl.service.OrderOperateHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单操作历史记录表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Tag(name = "order-operate-history API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/order-operate-history")
public class OrderOperateHistoryController extends BaseController<OrderOperateHistoryService, OrderOperateHistory, OrderOperateHistory, OrderOperateHistoryQuery, OrderOperateHistory, OrderOperateHistory> {

}

