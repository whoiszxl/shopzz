package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.cqrs.admin.query.OrderOperateHistoryQuery;
import com.whoiszxl.taowu.entity.OrderOperateHistory;
import com.whoiszxl.taowu.service.OrderOperateHistoryService;
import com.whoiszxl.taowu.common.base.BaseController;
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

