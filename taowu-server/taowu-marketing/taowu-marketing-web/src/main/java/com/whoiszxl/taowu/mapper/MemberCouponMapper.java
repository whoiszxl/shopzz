package com.whoiszxl.taowu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.taowu.entity.MemberCoupon;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 用户领取优惠券表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
public interface MemberCouponMapper extends BaseMapper<MemberCoupon> {

    @Update("update spms_member_coupon " +
            "set order_id = #{orderId}, " +
            "status = 2 " +
            "where member_id = #{memberId} " +
            "and coupon_id = #{couponId} " +
            "and status = 1 " +
            "and order_id is null")
    int writeOffCoupon(Long memberId, Long couponId, Long orderId);
}
