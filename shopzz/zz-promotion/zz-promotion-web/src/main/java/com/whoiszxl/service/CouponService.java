package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.Coupon;
import com.whoiszxl.entity.CouponReceivedRecord;
import com.whoiszxl.entity.MemberCoupon;

import java.util.List;

/**
 * <p>
 * 优惠券表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
public interface CouponService extends IService<Coupon> {

    /**
     * 通过会员ID和是否使用获取当前用户的优惠券列表
     * @param memberId 用户ID
     * @param isUsed 是否使用
     * @return 当前用户的优惠券列表
     */
    List<MemberCoupon> listByMemberIdAndIsUsed(Long memberId, Integer isUsed);

    /**
     * 给用户发放优惠券
     * @param memberId 用户ID
     * @param couponId 优惠券ID
     * @return 是否发放成功
     */
    Boolean giveCouponToMember(Long memberId, Long couponId);

    /**
     * 通过用户ID和优惠券ID更新领取记录
     * @param receivedRecord 领取记录
     * @return
     */
    Boolean updateByMemberIdAndCouponId(CouponReceivedRecord receivedRecord);
}
