package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.cqrs.admin.query.OrderReturnApplyQuery;
import com.whoiszxl.taowu.entity.OrderReturnApply;
import com.whoiszxl.taowu.service.OrderReturnApplyService;
import com.whoiszxl.taowu.common.base.BaseController;
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
