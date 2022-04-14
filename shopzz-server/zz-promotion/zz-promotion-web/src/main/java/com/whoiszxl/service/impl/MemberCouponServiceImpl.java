package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.MemberCoupon;
import com.whoiszxl.mapper.MemberCouponMapper;
import com.whoiszxl.service.MemberCouponService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MemberCouponServiceImpl extends ServiceImpl<MemberCouponMapper, MemberCoupon> implements MemberCouponService {

    @Autowired
    private MemberCouponMapper memberCouponMapper;


    @Override
    public int writeOffCoupon(Long memberId, Long couponId, Long orderId) {
        return memberCouponMapper.writeOffCoupon(memberId, couponId, orderId);
    }
}
