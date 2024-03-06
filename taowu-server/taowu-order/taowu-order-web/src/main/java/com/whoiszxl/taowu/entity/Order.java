package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Data
@TableName("oms_order")
@Schema(description = "订单表")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "订单状态，1：待付款，2：已取消，3：待发货，4：待收货，5：已完成，6：售后中（退货申请待审核），7：交易关闭（退货审核不通过），8：交易中（待寄送退货商品），9：售后中（退货商品待收货），10：售后中（退货待入库），11：（1）售后中（退货已入库），12：交易关闭（完成退款）")
    private Integer orderStatus;

    @Schema(description = "地址信息快照")
    private String snapshotAddress;

    @Schema(description = "支付方式: [1:支付宝 2:微信 3:数字货币]")
    private Integer payType;

    @Schema(description = "订单总额")
    private BigDecimal totalAmount;

    @Schema(description = "运费金额")
    private BigDecimal freightAmount;

    @Schema(description = "促销折扣金额")
    private BigDecimal promotionAmount;

    @Schema(description = "积分抵扣金额")
    private BigDecimal pointAmount;

    @Schema(description = "优惠券抵扣金额")
    private BigDecimal couponAmount;

    @Schema(description = "后台调整订单使用的折扣金额")
    private BigDecimal adminDiscountAmount;

    @Schema(description = "最终订单应付总额")
    private BigDecimal finalPayAmount;

    @Schema(description = "订单备注")
    private String orderComment;

    @Schema(description = "支付时间")
    private LocalDateTime paymentTime;

    @Schema(description = "发货时间")
    private LocalDateTime deliveryTime;

    @Schema(description = "确认收货时间")
    private LocalDateTime receiveTime;

    @Schema(description = "评价时间")
    private LocalDateTime commentTime;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
