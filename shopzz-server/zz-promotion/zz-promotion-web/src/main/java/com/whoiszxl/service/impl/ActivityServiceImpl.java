package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.response.ActivityApiResponse;
import com.whoiszxl.cqrs.response.CouponApiResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Activity;
import com.whoiszxl.entity.ActivityCoupon;
import com.whoiszxl.entity.Coupon;
import com.whoiszxl.mapper.ActivityMapper;
import com.whoiszxl.service.ActivityCouponService;
import com.whoiszxl.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.service.CouponService;
import com.whoiszxl.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whoiszxl.utils.JsonUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 促销活动表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    private CouponService couponService;

    @Autowired
    private ActivityCouponService activityCouponService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public ActivityApiResponse detail(Long id) {
        String redisKey = RedisKeyPrefixConstants.ACTIVITY_DETAIL + id;
        String activityJson = redisUtils.get(redisKey);

        if(StringUtils.isBlank(activityJson)) {
            synchronized (this) {
                activityJson = redisUtils.get(redisKey);
                if(StringUtils.isBlank(activityJson)) {
                    //从数据库拿数据
                    ActivityApiResponse response = new ActivityApiResponse();

                    Activity activity = super.getById(id);
                    dozerUtils.map(activity, response);

                    List<ActivityCoupon> activityCouponList = activityCouponService.list(Wrappers.<ActivityCoupon>lambdaQuery()
                            .eq(ActivityCoupon::getActivityId, id));
                    List<Long> couponIdList = activityCouponList.stream().map(e -> e.getCouponId()).collect(Collectors.toList());
                    List<Coupon> couponList = couponService.listByIds(couponIdList);

                    List<CouponApiResponse> couponApiResponseList = dozerUtils.mapList(couponList, CouponApiResponse.class);
                    response.setCouponList(couponApiResponseList);

                    activityJson = JsonUtil.toJson(response);

                    redisUtils.set(redisKey, activityJson);
                }
            }
        }

        return JsonUtil.fromJson(activityJson, ActivityApiResponse.class);
    }
}
