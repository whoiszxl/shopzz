package com.whoiszxl.controller.admin;


import com.whoiszxl.core.base.BaseController;
import com.whoiszxl.cqrs.admin.query.OrderInvoiceQuery;
import com.whoiszxl.entity.OrderInvoice;
import com.whoiszxl.service.OrderInvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单发票表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Tag(name = "order-invoice API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-invoice")
public class OrderInvoiceController extends BaseController<OrderInvoiceService, OrderInvoice, OrderInvoice, OrderInvoiceQuery, OrderInvoice, OrderInvoice> {

}

