package com.whoiszxl.client;

import cn.dev33.satoken.stp.StpUtil;
import com.whoiszxl.constants.CouponConstants;
import com.whoiszxl.dto.ActivityDTO;
import com.whoiszxl.dto.MemberCouponDTO;
import com.whoiszxl.entity.MemberCoupon;
import com.whoiszxl.feign.PromotionFeignClient;
import com.whoiszxl.service.CouponService;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 促销feign实现
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@RestController
public class PromotionFeignClientImpl implements PromotionFeignClient {


    @Autowired
    private CouponService couponService;

    @Override
    public List<MemberCouponDTO> getMyCoupon() {
        Long memberId = StpUtil.getLoginIdAsLong();
        List<MemberCoupon> memberCoupons = couponService.listByMemberIdAndIsUsed(memberId, CouponConstants.UseStatus.NOT_USED);
        return BeanCopierUtils.copyListProperties(memberCoupons, MemberCouponDTO::new);
    }

    @Override
    public List<ActivityDTO> listCurrentActivity() {
        return null;
    }
}
