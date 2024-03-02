package com.whoiszxl.controller.admin;


import com.whoiszxl.core.base.BaseController;
import com.whoiszxl.cqrs.admin.query.OrderItemQuery;
import com.whoiszxl.cqrs.admin.query.OrderReturnApplyQuery;
import com.whoiszxl.entity.OrderOperateHistory;
import com.whoiszxl.entity.OrderReturnApply;
import com.whoiszxl.service.OrderItemService;
import com.whoiszxl.service.OrderReturnApplyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 订单退货表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Tag(name = "order-return-apply API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-return-apply")
public class OrderReturnApplyController extends BaseController<OrderReturnApplyService, OrderReturnApply, OrderReturnApply, OrderReturnApplyQuery, OrderReturnApply, OrderReturnApply> {

}
