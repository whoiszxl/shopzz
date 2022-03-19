package com.whoiszxl.entity.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-01-09
 */
@Data
@ApiModel(value = "OrderVO对象", description = "OrderVO对象")
public class OrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单item列表")
    private List<OrderItemVO> orderItemVOList;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("订单状态，1：待付款，2：已取消，3：待发货，4：待收货，5：已完成，6：售后中（退货申请待审核），7：交易关闭（退货审核不通过），8：交易中（待寄送退货商品），9：售后中（退货商品待收货），10：售后中（退货待入库），11：（1）售后中（退货已入库），12：交易关闭（完成退款）")
    private Integer orderStatus;

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    private String receiverPhone;

    @ApiModelProperty("收货人邮编")
    private String receiverPostCode;

    @ApiModelProperty("省份/直辖市")
    private String receiverProvince;

    @ApiModelProperty("城市")
    private String receiverCity;

    @ApiModelProperty("区")
    private String receiverRegion;

    @ApiModelProperty("详细地址")
    private String receiverDetailAddress;

    @ApiModelProperty("支付方式: [1:支付宝 2:微信 3:数字货币]")
    private Integer payType;

    @ApiModelProperty("订单总额")
    private BigDecimal totalAmount;

    @ApiModelProperty("运费金额")
    private BigDecimal freightAmount;

    @ApiModelProperty("促销折扣金额")
    private BigDecimal promotionAmount;

    @ApiModelProperty("积分抵扣金额")
    private BigDecimal pointAmount;

    @ApiModelProperty("优惠券抵扣金额")
    private BigDecimal couponAmount;

    @ApiModelProperty("后台调整订单使用的折扣金额")
    private BigDecimal adminDiscountAmount;

    @ApiModelProperty("应付总额")
    private BigDecimal payAmount;

    @ApiModelProperty("发票类型[0->不开发票；1->电子发票；2->纸质发票]")
    private Integer invoiceType;

    @ApiModelProperty("发票抬头")
    private String invoiceHeader;

    @ApiModelProperty("发票内容")
    private String invoiceContent;

    @ApiModelProperty("收票人电话")
    private String invoiceReceiverPhone;

    @ApiModelProperty("收票人邮箱")
    private String invoiceReceiverEmail;

    @ApiModelProperty("收票人收货地址")
    private String invoiceReceiverAddress;

    @ApiModelProperty("订单备注")
    private String orderComment;

    @ApiModelProperty("支付时间")
    private LocalDateTime paymentTime;

    @ApiModelProperty("发货时间")
    private LocalDateTime deliveryTime;

    @ApiModelProperty("确认收货时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty("评价时间")
    private LocalDateTime commentTime;

}
