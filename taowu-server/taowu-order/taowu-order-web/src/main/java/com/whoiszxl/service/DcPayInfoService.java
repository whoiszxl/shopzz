package com.whoiszxl.service;

import com.whoiszxl.entity.PayInfoDc;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 数字货币支付信息表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
public interface DcPayInfoService extends IService<PayInfoDc> {

    /**
     * 通过订单ID和会员ID获取DC支付信息
     * @param orderId 订单ID
     * @param memberId 会员ID
     * @return
     */
    PayInfoDc getByOrderIdAndMemberId(Long orderId, Long memberId);
}
