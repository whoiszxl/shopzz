package com.whoiszxl.pay.entity;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-31
 **/
public enum TradeStatus {
    SUCCESS,  // 业务交易支付 明确成功

    FAILED,  // 业务交易支付 明确失败，

    UNKNOWN, // 业务交易状态未知，

    USERPAYING, //交易新建，等待支付

    REVOKED, //交易已撤销
}
