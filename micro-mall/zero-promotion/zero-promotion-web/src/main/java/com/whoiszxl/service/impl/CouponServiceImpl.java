package com.whoiszxl.service.impl;

import com.whoiszxl.entity.Coupon;
import com.whoiszxl.entity.CouponReceivedRecord;
import com.whoiszxl.entity.MemberCoupon;
import com.whoiszxl.mapper.CouponMapper;
import com.whoiszxl.service.CouponReceivedRecordService;
import com.whoiszxl.service.CouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 优惠券表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponReceivedRecordService couponReceivedRecordService;

    @Override
    public List<MemberCoupon> listByMemberIdAndIsUsed(Long memberId, Integer isUsed) {
        return couponMapper.listByMemberIdAndIsUsed(memberId, isUsed);
    }

    @Override
    public void giveCouponToMember(Long memberId, Long couponId) {
        CouponReceivedRecord receivedRecord = new CouponReceivedRecord();
        receivedRecord.setCouponId(couponId);
        receivedRecord.setMemberId(memberId);
        receivedRecord.setIsUsed(0);
        receivedRecord.setUsedTime(null);
        couponReceivedRecordService.save(receivedRecord);
    }
}
