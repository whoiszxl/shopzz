package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * oms_order
 * @author 
 */
@Data
public class PayInfoVO extends AbstractObject implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 订单状态，1：待付款，2：已取消，3：待发货，4：待收货，5：已完成，6：售后中（退货申请待审核），7：交易关闭（退货审核不通过），8：交易中（待寄送退货商品），9：售后中（退货商品待收货），10：售后中（退货待入库），11：（1）售后中（退货已入库），12：交易关闭（完成退款）
     */
    private Integer orderStatus;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 收货人邮编
     */
    private String receiverPostCode;

    /**
     * 省份/直辖市
     */
    private String receiverProvince;

    /**
     * 城市
     */
    private String receiverCity;

    /**
     * 区
     */
    private String receiverRegion;

    /**
     * 详细地址
     */
    private String receiverDetailAddress;

    /**
     * 支付方式: [1:支付宝 2:微信 3:数字货币]
     */
    private Integer payType;

    /**
     * 订单总额
     */
    private BigDecimal totalAmount;

    /**
     * 运费金额
     */
    private BigDecimal freightAmount;

    /**
     * 促销折扣金额
     */
    private BigDecimal promotionAmount;

    /**
     * 积分抵扣金额
     */
    private BigDecimal pointAmount;

    /**
     * 优惠券抵扣金额
     */
    private BigDecimal couponAmount;

    /**
     * 后台调整订单使用的折扣金额
     */
    private BigDecimal adminDiscountAmount;

    /**
     * 应付总额
     */
    private BigDecimal payAmount;

    /**
     * 发票类型[0->不开发票；1->电子发票；2->纸质发票]
     */
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceHeader;

    /**
     * 发票内容
     */
    private String invoiceContent;

    /**
     * 收票人电话
     */
    private String invoiceReceiverPhone;

    /**
     * 收票人邮箱
     */
    private String invoiceReceiverEmail;

    /**
     * 收票人收货地址
     */
    private String invoiceReceiverAddress;

    /**
     * 订单备注
     */
    private String orderComment;

    /**
     * 支付时间
     */
    private Date paymentTime;

    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 确认收货时间
     */
    private Date receiveTime;

    /**
     * 评价时间
     */
    private Date commentTime;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    private static final long serialVersionUID = 1L;
}