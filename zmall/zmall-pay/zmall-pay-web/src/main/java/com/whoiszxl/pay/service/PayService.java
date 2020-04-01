package com.whoiszxl.pay.service;

import com.whoiszxl.pay.entity.PaymentResponseDTO;

public interface PayService {

    /**
     * 原生支付
     * @param orderId 订单ID
     * @param money 支付金额（单位：元）
     * @return
     */
    PaymentResponseDTO nativePay(String orderId, String money);

    /**
     * 查询支付宝订单状态
     * @param outTradeNo ZMALL的商家订单号
     * @return
     */
    PaymentResponseDTO queryPayOrderByAli(String outTradeNo);
}
