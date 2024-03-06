package com.whoiszxl.taowu.feign;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.feign.FeignTokenConfig;
import com.whoiszxl.taowu.dto.CouponFeignDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品feign接口
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@FeignClient(name = "taowu-marketing", contextId = "marketingFeign", configuration = FeignTokenConfig.class)
public interface PromotionFeignClient {


    /**
     * 获取coupon信息
     * @param couponId coupon id
     * @return
     */
    @GetMapping("/getCoupon")
    ResponseResult<CouponFeignDTO> getCoupon(@RequestParam Long couponId);

    /**
     * 校验是否领取过、使用过
     * @param couponId 优惠券ID
     * @return
     */
    @GetMapping("/checkCouponIsUsed")
    ResponseResult<Boolean> checkCouponIsUsed(Long couponId);

    @PostMapping("/writeOffCoupon")
    ResponseResult<Boolean> writeOffCoupon(@RequestParam Long couponId, @RequestParam Long orderId);
}
