package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.reflect.TypeToken;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.enums.promotion.CouponFullLimitedEnum;
import com.whoiszxl.enums.promotion.CouponStatusEnum;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.response.MyCouponApiResponse;
import com.whoiszxl.cqrs.vo.CouponApiVO;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Coupon;
import com.whoiszxl.entity.CouponCategory;
import com.whoiszxl.entity.MemberCoupon;
import com.whoiszxl.exception.ExceptionCatcher;
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
    public List<CouponApiVO> getCouponByCategoryId(Long categoryId) {
        List<CouponCategory> couponCategorieList = couponCategoryMapper.selectList(
                Wrappers.<CouponCategory>lambdaQuery().eq(CouponCategory::getCategoryId, categoryId));
        if(couponCategorieList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> couponIdList = couponCategorieList.stream().map(CouponCategory::getCouponId).collect(Collectors.toList());
        List<Coupon> couponList = super.listByIds(couponIdList);
        List<CouponApiVO> couponApiVOList = dozerUtils.mapList(couponList, CouponApiVO.class);
        return couponApiVOList;
    }


    @Override
    public List<CouponApiVO> getCouponAllUnlimited() {
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

                    List<CouponApiVO> couponApiVOList = dozerUtils.mapList(couponList, CouponApiVO.class);
                    allUnlimitedCouponListJson = JsonUtil.toJson(couponApiVOList);

                    redisUtils.set(redisKey, allUnlimitedCouponListJson);
                }
            }
        }

        Type collectionType = new TypeToken<List<CouponApiVO>>(){}.getType();
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
            this.update(Wrappers.<Coupon>lambdaUpdate()
                    .set(Coupon::getStatus, CouponStatusEnum.EXPIRED.getCode())
                    .eq(Coupon::getId, coupon.getId()));
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("优惠券已过期"));
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
        return couponMapper.myCouponList(memberId, status);
    }
}
