package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.CouponConstants;
import com.whoiszxl.entity.Coupon;
import com.whoiszxl.entity.CouponReceivedRecord;
import com.whoiszxl.entity.MemberCoupon;
import com.whoiszxl.entity.query.CouponQuery;
import com.whoiszxl.entity.vo.CouponVO;
import com.whoiszxl.service.CouponReceivedRecordService;
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

    @Autowired
    private CouponReceivedRecordService couponReceivedRecordService;

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "分页获取当前用户的优惠券列表", notes = "分页获取当前用户的优惠券列表", response = CouponVO.class)
    public ResponseResult<List<MemberCoupon>> list(@RequestBody CouponQuery couponQuery) {
        Long memberId = StpUtil.getLoginIdAsLong();
        List<MemberCoupon> couponList = couponService.listByMemberIdAndIsUsed(memberId, couponQuery.getIsUsed());
        return ResponseResult.buildSuccess(couponList);
    }

    @SaCheckLogin
    @PostMapping("/{couponId}")
    @ApiOperation(value = "用户领取优惠券", notes = "用户领取优惠券", response = Boolean.class)
    public ResponseResult<Boolean> list(@PathVariable Long couponId) {
        //1. 校验优惠券是否能被领取
        Coupon coupon = couponService.getById(couponId);
        if(coupon.getType().equals(CouponConstants.GiveType.ONLY_GIVE) ||
                !coupon.getStatus().equals(CouponConstants.Status.GIVING) ||
                coupon.getAllCount() - coupon.getReceivedCount() == 0) {
            return ResponseResult.buildError("当前优惠券不能被领取");
        }

        Long memberId = StpUtil.getLoginIdAsLong();

        //2. 判断用户是否领取过
        CouponReceivedRecord couponReceivedRecord = couponReceivedRecordService.getByCouponIdAndMemberId(couponId, memberId);
        if(couponReceivedRecord != null) {
            return ResponseResult.buildError("你已经领取过了此优惠券");
        }

        //3. 发放优惠券
        Boolean giveFlag = couponService.giveCouponToMember(StpUtil.getLoginIdAsLong(), couponId);
        return ResponseResult.buildByFlag(giveFlag);
    }

}

