package com.whoiszxl.taowu.controller.admin;


import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.cqrs.command.CouponSaveCommand;
import com.whoiszxl.taowu.cqrs.command.CouponUpdateCommand;
import com.whoiszxl.taowu.cqrs.query.CouponQuery;
import com.whoiszxl.taowu.cqrs.response.CouponResponse;
import com.whoiszxl.taowu.entity.Coupon;
import com.whoiszxl.taowu.service.CouponService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 优惠券表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/coupon")
@Tag(name = "优惠券相关接口")
@RequiredArgsConstructor
public class CouponAdminController extends BaseController<CouponService, Coupon, CouponResponse, CouponQuery, CouponSaveCommand, CouponUpdateCommand> {

}

