package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.response.ActivityApiResponse;
import com.whoiszxl.taowu.cqrs.vo.CouponApiVO;
import com.whoiszxl.taowu.entity.Activity;
import com.whoiszxl.taowu.entity.ActivityCoupon;
import com.whoiszxl.taowu.entity.Coupon;
import com.whoiszxl.taowu.mapper.ActivityMapper;
import com.whoiszxl.taowu.service.ActivityCouponService;
import com.whoiszxl.taowu.service.ActivityService;
import com.whoiszxl.taowu.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    private final CouponService couponService;

    private final ActivityCouponService activityCouponService;

    private final RedisUtils redisUtils;

    @Override
    public ActivityApiResponse detail(Long id) {
        String redisKey = RedisPrefixConstants.Marketing.ACTIVITY_DETAIL + id;
        String activityJson = redisUtils.get(redisKey);

        if(StringUtils.isBlank(activityJson)) {
            synchronized (this) {
                activityJson = redisUtils.get(redisKey);
                if(StringUtils.isBlank(activityJson)) {
                    //从数据库拿数据
                    ActivityApiResponse response = new ActivityApiResponse();

                    Activity activity = super.getById(id);
                    BeanUtil.copyProperties(activity, response);

                    List<ActivityCoupon> activityCouponList = activityCouponService.list(Wrappers.<ActivityCoupon>lambdaQuery()
                            .eq(ActivityCoupon::getActivityId, id));
                    List<Long> couponIdList = activityCouponList.stream().map(ActivityCoupon::getCouponId).collect(Collectors.toList());
                    List<Coupon> couponList = couponService.listByIds(couponIdList);

                    List<CouponApiVO> couponApiVOList = BeanUtil.copyToList(couponList, CouponApiVO.class);

                    response.setCouponList(couponApiVOList);

                    activityJson = JsonUtil.toJson(response);

                    redisUtils.set(redisKey, activityJson);
                }
            }
        }

        return JsonUtil.fromJson(activityJson, ActivityApiResponse.class);
    }
}
