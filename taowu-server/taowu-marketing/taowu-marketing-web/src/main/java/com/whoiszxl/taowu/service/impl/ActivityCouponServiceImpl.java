package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.ActivityCoupon;
import com.whoiszxl.taowu.mapper.ActivityCouponMapper;
import com.whoiszxl.taowu.service.ActivityCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 促销活动跟优惠券的关联关系表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Service
@RequiredArgsConstructor
public class ActivityCouponServiceImpl extends ServiceImpl<ActivityCouponMapper, ActivityCoupon> implements ActivityCouponService {

}
