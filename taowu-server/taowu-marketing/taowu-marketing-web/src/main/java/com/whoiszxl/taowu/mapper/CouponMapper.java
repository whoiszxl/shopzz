package com.whoiszxl.taowu.mapper;

import com.whoiszxl.taowu.cqrs.response.MyCouponApiResponse;
import com.whoiszxl.taowu.entity.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券表 Mapper 接口
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
public interface CouponMapper extends BaseMapper<Coupon> {

    /**
     * 通过会员ID和状态获取优惠券列表
     * @param memberId
     * @param status
     * @return
     */
    List<MyCouponApiResponse> myCouponList(@Param("memberId") Long memberId, @Param("status") Integer status);
}
