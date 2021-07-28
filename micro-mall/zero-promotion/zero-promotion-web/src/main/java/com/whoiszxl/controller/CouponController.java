package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Coupon;
import com.whoiszxl.entity.MemberCoupon;
import com.whoiszxl.entity.query.CouponQuery;
import com.whoiszxl.entity.vo.CouponVO;
import com.whoiszxl.service.CouponService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 优惠券表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "分页获取当前用户的优惠券列表", notes = "分页获取当前用户的优惠券列表", response = CouponVO.class)
    public ResponseResult<List<MemberCoupon>> list(@RequestBody CouponQuery couponQuery) {
        Long memberId = StpUtil.getLoginIdAsLong();
        List<MemberCoupon> couponList = couponService.listByMemberIdAndIsUsed(memberId, couponQuery.getIsUsed());
        return ResponseResult.buildSuccess(couponList);
    }

}

