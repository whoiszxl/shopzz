package com.whoiszxl.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.dto.CouponFeignDTO;
import com.whoiszxl.entity.Coupon;
import com.whoiszxl.entity.CouponCategory;
import com.whoiszxl.entity.MemberCoupon;
import com.whoiszxl.service.CouponCategoryService;
import com.whoiszxl.service.CouponService;
import com.whoiszxl.service.MemberCouponService;
import com.whoiszxl.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PromotionFeignClientImpl implements PromotionFeignClient{

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponCategoryService couponCategoryService;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public ResponseResult<CouponFeignDTO> getCoupon(Long couponId) {
        Coupon coupon = couponService.getById(couponId);
        CouponFeignDTO couponFeignDTO = dozerUtils.map(coupon, CouponFeignDTO.class);

        List<CouponCategory> couponCategoryList = couponCategoryService.list(Wrappers.<CouponCategory>lambdaQuery()
                .eq(CouponCategory::getCouponId, couponId));
        List<Long> categoryIdList = couponCategoryList.stream().map(CouponCategory::getCategoryId).collect(Collectors.toList());

        couponFeignDTO.setCategoryIdList(categoryIdList);
        return ResponseResult.buildSuccess(couponFeignDTO);
    }

    @Override
    public ResponseResult<Boolean> checkCouponIsUsed(Long couponId) {
        Long memberId = AuthUtils.getMemberId();
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
        int result = memberCouponService.writeOffCoupon(AuthUtils.getMemberId(), couponId, orderId);
        return result > 0 ? ResponseResult.buildSuccess() : ResponseResult.buildError();
    }
}
