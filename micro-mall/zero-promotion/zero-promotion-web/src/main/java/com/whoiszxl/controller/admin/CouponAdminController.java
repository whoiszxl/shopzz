package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.CouponConstants;
import com.whoiszxl.entity.Coupon;
import com.whoiszxl.entity.MemberCoupon;
import com.whoiszxl.entity.query.CouponQuery;
import com.whoiszxl.entity.vo.CouponVO;
import com.whoiszxl.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/coupon")
@Api(tags = "管理员端: 优惠券管理相关接口")
public class CouponAdminController {

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

    @SaCheckPermission("admin")
    @PostMapping
    @ApiOperation(value = "管理后台新增优惠券", notes = "管理后台新增优惠券", response = Boolean.class)
    public ResponseResult<Boolean> save(@RequestBody Coupon coupon) {
        boolean saveFlag = couponService.save(coupon);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckPermission("admin")
    @PutMapping
    @ApiOperation(value = "管理后台更新优惠券", notes = "管理后台更新优惠券", response = Boolean.class)
    public ResponseResult<Boolean> update(@RequestBody Coupon coupon) {
        boolean updateFlag = couponService.updateById(coupon);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckPermission("admin")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "管理后台删除优惠券", notes = "管理后台删除优惠券", response = Boolean.class)
    public ResponseResult<Boolean> remmove(@PathVariable Long id) {
        boolean removeFlag = couponService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

    @SaCheckPermission("admin")
    @PostMapping("/{memberId}/{couponId}")
    @ApiOperation(value = "后台单独给用户发放优惠券", notes = "后台单独给用户发放优惠券", response = Boolean.class)
    public ResponseResult<Boolean> give(@PathVariable Long memberId, @PathVariable Long couponId) {
        //1. 校验优惠券是否支持后台发放
        Coupon coupon = couponService.getById(couponId);
        if(coupon.getType().equals(CouponConstants.GiveType.ONLY_RECEIVE) ||
                !coupon.getStatus().equals(CouponConstants.Status.GIVING)) {
            return ResponseResult.buildError("当前优惠券不支持后台发放");
        }

        //2. 发放优惠券
        couponService.giveCouponToMember(memberId, couponId);
        return ResponseResult.buildSuccess();
    }
}

