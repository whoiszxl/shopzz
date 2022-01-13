package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.entity.CouponReceivedRecord;
import com.whoiszxl.mapper.CouponReceivedRecordMapper;
import com.whoiszxl.service.CouponReceivedRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠券领取记录表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Service
public class CouponReceivedRecordServiceImpl extends ServiceImpl<CouponReceivedRecordMapper, CouponReceivedRecord> implements CouponReceivedRecordService {

    @Override
    public CouponReceivedRecord getByCouponIdAndMemberId(Long couponId, Long memberId) {
        LambdaQueryWrapper<CouponReceivedRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CouponReceivedRecord::getCouponId, couponId);
        lambdaQueryWrapper.eq(CouponReceivedRecord::getMemberId, memberId);
        return this.getOne(lambdaQueryWrapper);
    }
}
