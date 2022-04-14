package com.whoiszxl.feign;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.config.OAuth2FeignConfig;
import com.whoiszxl.dto.CouponFeignDTO;
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
@FeignClient(name = "zz-promotion-web", contextId = "promotionFeign", configuration = OAuth2FeignConfig.class)
public interface PromotionFeignClient {


    /**
     * 根据spuId获取SPU详细信息
     * @param spuId spu id
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
