package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.CouponSaveCommand;
import com.whoiszxl.cqrs.command.CouponUpdateCommand;
import com.whoiszxl.cqrs.query.CouponQuery;
import com.whoiszxl.cqrs.response.CouponApiResponse;
import com.whoiszxl.cqrs.response.CouponResponse;
import com.whoiszxl.cqrs.response.MyCouponApiResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Coupon;
import com.whoiszxl.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "C端:优惠券相关接口")
public class CouponApiController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @GetMapping("/by/category/{categoryId}")
    @ApiOperation(value = "通过分类ID获取优惠券列表", notes = "通过分类ID获取优惠券列表", response = CouponApiResponse.class)
    public ResponseResult<List<CouponApiResponse>> getCouponByCategoryId(@PathVariable Long categoryId) {
        List<CouponApiResponse> couponApiResponseList = couponService.getCouponByCategoryId(categoryId);
        return ResponseResult.buildSuccess(couponApiResponseList);
    }


    @SaCheckLogin
    @GetMapping("/all/unlimited")
    @ApiOperation(value = "获取所有全场通用的优惠券", notes = "获取所有全场通用的优惠券", response = CouponApiResponse.class)
    public ResponseResult<List<CouponApiResponse>> getCouponAllUnlimited() {
        List<CouponApiResponse> couponApiResponseList = couponService.getCouponAllUnlimited();
        return ResponseResult.buildSuccess(couponApiResponseList);
    }

    @SaCheckLogin
    @PostMapping("/receive/{couponId}")
    @ApiOperation(value = "领取优惠券", notes = "指定优惠券ID领取优惠券", response = ResponseResult.class)
    public ResponseResult<Boolean> receive(@PathVariable Long couponId) {
        couponService.receive(couponId);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @PostMapping("/my/{status}")
    @ApiOperation(value = "获取我的优惠券列表", notes = "指定优惠券使用状态获取优惠券列表", response = MyCouponApiResponse.class)
    public ResponseResult<List<MyCouponApiResponse>> myCouponList(@PathVariable Integer status) {
        List<MyCouponApiResponse> responseList = couponService.myCouponList(status);
        return ResponseResult.buildSuccess(responseList);
    }
}

