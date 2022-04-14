package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.MemberCoupon;

/**
 * <p>
 * 用户领取优惠券表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
public interface MemberCouponService extends IService<MemberCoupon> {

    /**
     * 核销优惠券
     * @param memberId 用户ID
     * @param couponId 优惠券ID
     * @param orderId 订单ID
     * @return
     */
    int writeOffCoupon(Long memberId, Long couponId, Long orderId);
}
