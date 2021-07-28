package com.whoiszxl.mapper;

import com.whoiszxl.entity.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whoiszxl.entity.MemberCoupon;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 优惠券表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
public interface CouponMapper extends BaseMapper<Coupon> {

    @Select("select pcrr.id, pcrr.member_id, pcrr.used_time, pcrr.created_at, pc.name, pc.type, pc.rule, pc.start_time, pc.end_time " +
            "from promotion_coupon_received_record pcrr " +
            "left join promotion_coupon pc " +
            "on pcrr.coupon_id = pc.id " +
            "where pcrr.member_id = #{memberId} and pcrr.is_used = #{isUsed}")
    List<MemberCoupon> listByMemberIdAndIsUsed(Long memberId, Integer isUsed);
}
