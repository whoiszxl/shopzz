package com.whoiszxl.taowu.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.dto.CouponFeignDTO;
import com.whoiszxl.taowu.entity.Coupon;
import com.whoiszxl.taowu.entity.CouponCategory;
import com.whoiszxl.taowu.entity.MemberCoupon;
import com.whoiszxl.taowu.service.CouponCategoryService;
import com.whoiszxl.taowu.service.CouponService;
import com.whoiszxl.taowu.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品feign client 实现
 *
 * @author whoiszxl
 * @date 2022/3/31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PromotionFeignClientImpl implements PromotionFeignClient{

    private final CouponService couponService;

    private final MemberCouponService memberCouponService;

    private final CouponCategoryService couponCategoryService;

    private final TokenHelper tokenHelper;

    @Override
    public ResponseResult<CouponFeignDTO> getCoupon(Long couponId) {
        Coupon coupon = couponService.getById(couponId);
        CouponFeignDTO couponFeignDTO = BeanUtil.copyProperties(coupon, CouponFeignDTO.class);

        List<CouponCategory> couponCategoryList = couponCategoryService.list(Wrappers.<CouponCategory>lambdaQuery()
                .eq(CouponCategory::getCouponId, couponId));
        List<Long> categoryIdList = couponCategoryList.stream().map(CouponCategory::getCategoryId).collect(Collectors.toList());

        couponFeignDTO.setCategoryIdList(categoryIdList);
        return ResponseResult.buildSuccess(couponFeignDTO);
    }

    @Override
    public ResponseResult<Boolean> checkCouponIsUsed(Long couponId) {
        Long memberId = tokenHelper.getAppMemberId();
        MemberCoupon memberCoupon = memberCouponService.getOne(Wrappers.<MemberCoupon>lambdaQuery()
                .eq(MemberCoupon::getCouponId, couponId)
                .eq(MemberCoupon::getMemberId, memberId));

        if(memberCoupon == null) {
            return ResponseResult.buildSuccess();
        }
        return ResponseResult.buildError();
    }

    @Override
    public ResponseResult<Boolean> writeOffCoupon(Long couponId, Long orderId) {
        int result = memberCouponService.writeOffCoupon(tokenHelper.getAppMemberId(), couponId, orderId);
        return result > 0 ? ResponseResult.buildSuccess() : ResponseResult.buildError();
    }
}
