package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.reflect.TypeToken;
import com.whoiszxl.constants.CouponFullLimitedEnum;
import com.whoiszxl.constants.CouponStatusEnum;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.response.CouponApiResponse;
import com.whoiszxl.cqrs.response.MyCouponApiResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Coupon;
import com.whoiszxl.entity.CouponCategory;
import com.whoiszxl.entity.MemberCoupon;
import com.whoiszxl.mapper.CouponCategoryMapper;
import com.whoiszxl.mapper.CouponMapper;
import com.whoiszxl.service.CouponService;
import com.whoiszxl.service.MemberCouponService;
import com.whoiszxl.utils.AssertUtils;
import com.whoiszxl.utils.AuthUtils;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private CouponCategoryMapper couponCategoryMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private MemberCouponService memberCouponService;

    @Override
    public List<CouponApiResponse> getCouponByCategoryId(Long categoryId) {
        List<CouponCategory> couponCategorieList = couponCategoryMapper.selectList(
                Wrappers.<CouponCategory>lambdaQuery().eq(CouponCategory::getCategoryId, categoryId));
        if(couponCategorieList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> couponIdList = couponCategorieList.stream().map(CouponCategory::getCouponId).collect(Collectors.toList());
        List<Coupon> couponList = super.listByIds(couponIdList);
        List<CouponApiResponse> couponApiResponseList = dozerUtils.mapList(couponList, CouponApiResponse.class);
        return couponApiResponseList;
    }


    @Override
    public List<CouponApiResponse> getCouponAllUnlimited() {
        String redisKey = RedisKeyPrefixConstants.ACTIVITY_NOTLIMIT_COUPONLIST;
        String allUnlimitedCouponListJson = redisUtils.get(redisKey);
        if(StringUtils.isBlank(allUnlimitedCouponListJson)) {

            synchronized (this) {
                allUnlimitedCouponListJson = redisUtils.get(redisKey);
                if(StringUtils.isBlank(allUnlimitedCouponListJson)) {
                    List<Coupon> couponList = super.list(
                            Wrappers.<Coupon>lambdaQuery()
                                    .eq(Coupon::getFullLimited, CouponFullLimitedEnum.NOT_LIMIT.getCode())
                                    .eq(Coupon::getStatus, CouponStatusEnum.AVAIL.getCode()));

                    if(couponList.isEmpty()) {
                        return Collections.emptyList();
                    }

                    List<CouponApiResponse> couponApiResponseList = dozerUtils.mapList(couponList, CouponApiResponse.class);
                    allUnlimitedCouponListJson = JsonUtil.toJson(couponApiResponseList);

                    redisUtils.set(redisKey, allUnlimitedCouponListJson);
                }
            }
        }

        Type collectionType = new TypeToken<List<CouponApiResponse>>(){}.getType();
        return JsonUtil.fromJsonToList(allUnlimitedCouponListJson, collectionType);
    }

    @Override
    public void receive(Long couponId) {
        //1. 校验优惠券是否存在，是否有效
        Coupon coupon = super.getOne(Wrappers.<Coupon>lambdaQuery()
                .eq(Coupon::getId, couponId)
                .eq(Coupon::getStatus, CouponStatusEnum.AVAIL.getCode()));
        AssertUtils.isTrue(coupon != null, "优惠券不存在");

        //2. 判断是否在有效期内
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(coupon.getStartTime()) || now.isAfter(coupon.getEndTime())) {
            //将优惠券更新为过期状态
            coupon.setStatus(CouponStatusEnum.EXPIRED.getCode());
            super.updateById(coupon);
            return;
        }

        //3. 判断是否领取过
        Long memberId = AuthUtils.getMemberId();
        MemberCoupon memberCoupon = memberCouponService.getOne(Wrappers.<MemberCoupon>lambdaQuery()
                .eq(MemberCoupon::getCouponId, couponId)
                .eq(MemberCoupon::getMemberId, memberId));
        AssertUtils.isTrue(memberCoupon == null, "不能重复领取");

        //4. 领取成功
        MemberCoupon saveParams = new MemberCoupon();
        saveParams.setCouponId(couponId);
        saveParams.setMemberId(memberId);
        saveParams.setGetTime(now);
        memberCouponService.save(saveParams);
    }


    @Override
    public List<MyCouponApiResponse> myCouponList(Integer status) {
        Long memberId = AuthUtils.getMemberId();
        List<MyCouponApiResponse> couponList = couponMapper.myCouponList(memberId, status);
        return couponList;
    }
}
