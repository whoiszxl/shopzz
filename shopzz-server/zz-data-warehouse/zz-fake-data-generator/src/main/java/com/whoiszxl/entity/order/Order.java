package com.whoiszxl.entity.order;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Getter
@Setter
@TableName("oms_order")
@ApiModel(value = "Order对象", description = "订单表")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("订单状态，1：待付款，2：已取消，3：待发货，4：待收货，5：已完成，6：售后中（退货申请待审核），7：交易关闭（退货审核不通过），8：交易中（待寄送退货商品），9：售后中（退货商品待收货），10：售后中（退货待入库），11：（1）售后中（退货已入库），12：交易关闭（完成退款）")
    private Integer orderStatus;

    @ApiModelProperty("地址信息快照")
    private String snapshotAddress;

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

    @ApiModelProperty("最终订单应付总额")
    private BigDecimal finalPayAmount;

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

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
