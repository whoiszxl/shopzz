package com.whoiszxl.controller.admin;


import com.whoiszxl.core.base.BaseController;
import com.whoiszxl.cqrs.admin.query.OrderQuery;
import com.whoiszxl.entity.Order;
import com.whoiszxl.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Tag(name = "order API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController extends BaseController<OrderService, Order, Order, OrderQuery, Order, Order> {

}