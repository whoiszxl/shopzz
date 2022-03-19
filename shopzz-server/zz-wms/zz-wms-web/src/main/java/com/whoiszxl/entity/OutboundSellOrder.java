package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 销售出库订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Getter
@Setter
@TableName("wms_outbound_sell_order")
@ApiModel(value = "OutboundSellOrder对象", description = "销售出库订单表")
public class OutboundSellOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("仓库ID")
    private Long warehouseId;

    @ApiModelProperty("货架ID")
    private Long warehouseShelfId;

    @ApiModelProperty("用户账号ID")
    private Long memberId;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("收货人")
    private String receiveName;

    @ApiModelProperty("收货地址")
    private String receiveAddress;

    @ApiModelProperty("收货人电话号码")
    private String receivePhone;

    @ApiModelProperty("快递单号")
    private String expressNo;

    @ApiModelProperty("快递公司编码")
    private String expressCode;

    @ApiModelProperty("运费")
    private BigDecimal expressFreight;

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

    @ApiModelProperty("订单备注")
    private String orderComment;

    @ApiModelProperty("销售出库单的状态, 1:编辑中, 2:待审核, 3:已完成")
    private Integer sellOutboundOrderStatus;

    @ApiModelProperty("发货时间")
    private LocalDateTime outboundTime;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除,  0: 未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
