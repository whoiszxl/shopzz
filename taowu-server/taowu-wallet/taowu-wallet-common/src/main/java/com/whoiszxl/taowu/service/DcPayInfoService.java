package com.whoiszxl.taowu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.entity.PayInfoDc;

import java.util.List;

/**
 * <p>
 * 数字货币支付信息表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
public interface DcPayInfoService extends IService<PayInfoDc> {

    /**
     * 通过订单ID和会员ID获取DC支付信息
     * @param orderId 订单ID
     * @param memberId 会员ID
     * @return
     */
    PayInfoDc getByOrderIdAndMemberId(Long orderId, Long memberId);

    /**
     * 通过币种名称和订单ID获取支付信息
     * @param currencyName 币种名称
     * @param orderId 订单ID
     * @return 支付信息
     */
    PayInfoDc getPayInfoByCurrencyNameAndOrderId(String currencyName, String orderId);

    /**
     * 通过币种名称获取待确认的支付单集合
     * @param currencyName 币种名称
     * @return 待确认的支付单集合
     */
    List<PayInfoDc> getWaitConfirmPayInfo(String currencyName);

    /**
     * 通过地址和币种名称和上链状态获取支付单信息
     * @param toAddress 地址
     * @param currencyName 币种名称
     * @param upchainStatus 上链状态
     * @return 支付单信息
     */
    PayInfoDc getRechargeByAddressAndCurrencyNameAndUpchainStatus(String toAddress, String currencyName, Integer upchainStatus);
}
