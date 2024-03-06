package com.whoiszxl.taowu.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.response.CouponApiResponse;
import com.whoiszxl.taowu.cqrs.response.MyCouponApiResponse;
import com.whoiszxl.taowu.cqrs.vo.CouponApiVO;
import com.whoiszxl.taowu.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 优惠券表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@RestController
@RequestMapping("/api/coupon")
@Tag(name = "C端:优惠券相关接口")
@RequiredArgsConstructor
public class CouponApiController {

    private final CouponService couponService;

    @SaCheckLogin
    @GetMapping("/by/category/{categoryId}")
    @Operation(summary = "通过分类ID获取优惠券列表", description = "通过分类ID获取优惠券列表")
    public ResponseResult<CouponApiResponse> getCouponByCategoryId(@PathVariable Long categoryId) {
        List<CouponApiVO> couponApiVOList = couponService.getCouponByCategoryId(categoryId);
        return ResponseResult.buildSuccess(new CouponApiResponse(couponApiVOList));
    }


    @SaCheckLogin
    @GetMapping("/all/unlimited")
    @Operation(summary = "获取所有全场通用的优惠券", description = "获取所有全场通用的优惠券")
    public ResponseResult<CouponApiResponse> getCouponAllUnlimited() {
        List<CouponApiVO> couponApiVOList = couponService.getCouponAllUnlimited();
        return ResponseResult.buildSuccess(new CouponApiResponse(couponApiVOList));
    }

    @SaCheckLogin
    @PostMapping("/receive/{couponId}")
    @Operation(summary = "领取优惠券", description = "领取优惠券")
    public ResponseResult<Boolean> receive(@PathVariable Long couponId) {
        couponService.receive(couponId);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @PostMapping("/my/{status}")
    @Operation(summary = "获取我的优惠券列表", description = "获取我的优惠券列表")
    public ResponseResult<List<MyCouponApiResponse>> myCouponList(@PathVariable Integer status) {
        List<MyCouponApiResponse> responseList = couponService.myCouponList(status);
        return ResponseResult.buildSuccess(responseList);
    }
}

