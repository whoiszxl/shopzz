package com.whoiszxl.service;

import com.whoiszxl.entity.CouponReceivedRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 优惠券领取记录表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
public interface CouponReceivedRecordService extends IService<CouponReceivedRecord> {

    /**
     * 通过优惠券ID和会员ID获取用户领取记录
     * @param couponId 优惠券ID
     * @param memberId 会员ID
     * @return 用户领取记录
     */
    CouponReceivedRecord getByCouponIdAndMemberId(Long couponId, Long memberId);
}
