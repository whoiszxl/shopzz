package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.cqrs.admin.query.OrderItemQuery;
import com.whoiszxl.taowu.entity.OrderItem;
import com.whoiszxl.taowu.service.OrderItemService;
import com.whoiszxl.taowu.common.base.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单明细表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Tag(name = "order-item API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-item")
public class OrderItemController extends BaseController<OrderItemService, OrderItem, OrderItem, OrderItemQuery, OrderItem, OrderItem> {


}

