package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.MemberCoupon;
import com.whoiszxl.taowu.mapper.MemberCouponMapper;
import com.whoiszxl.taowu.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户领取优惠券表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl extends ServiceImpl<MemberCouponMapper, MemberCoupon> implements MemberCouponService {

    private final MemberCouponMapper memberCouponMapper;

    @Override
    public int writeOffCoupon(Long memberId, Long couponId, Long orderId) {
        return memberCouponMapper.writeOffCoupon(memberId, couponId, orderId);
    }
}
