package com.whoiszxl.client;

import cn.dev33.satoken.stp.StpUtil;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.CouponConstants;
import com.whoiszxl.dto.ActivityDTO;
import com.whoiszxl.dto.MemberCouponDTO;
import com.whoiszxl.entity.CouponReceivedRecord;
import com.whoiszxl.entity.MemberCoupon;
import com.whoiszxl.feign.PromotionFeignClient;
import com.whoiszxl.service.CouponReceivedRecordService;
import com.whoiszxl.service.CouponService;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 促销feign实现
 *
 * @author whoiszxl
 * @date 2021/7/30
 */
@RestController
public class PromotionFeignClientImpl implements PromotionFeignClient {


    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponReceivedRecordService couponReceivedRecordService;

    @Override
    public List<MemberCouponDTO> getMyCoupon() {
        Long memberId = StpUtil.getLoginIdAsLong();
        List<MemberCoupon> memberCoupons = couponService.listByMemberIdAndIsUsed(memberId, CouponConstants.UseStatus.NOT_USED);
        return BeanCopierUtils.copyListProperties(memberCoupons, MemberCouponDTO::new);
    }

    @Override
    public List<ActivityDTO> listCurrentActivity() {
        return null;
    }

    @Override
    public ResponseResult<Boolean> useCoupon(String receivedRecordId) {
        long memberId = StpUtil.getLoginIdAsLong();

        CouponReceivedRecord receivedRecord = couponReceivedRecordService.getById(receivedRecordId);
        if(receivedRecord.getMemberId() != memberId || CouponConstants.UseStatus.USED.equals(receivedRecord.getIsUsed())) {
            return ResponseResult.buildError("优惠券不可用");
        }

        receivedRecord.setIsUsed(CouponConstants.UseStatus.USED);
        receivedRecord.setUsedTime(new Date());
        boolean updateFlag = couponReceivedRecordService.updateById(receivedRecord);
        return ResponseResult.buildByFlag(updateFlag);
    }
}
