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
 * 退货入库订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Getter
@Setter
@TableName("wms_inbound_return_order")
@ApiModel(value = "InboundReturnOrder对象", description = "退货入库订单表")
public class InboundReturnOrder implements Serializable {

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

    @ApiModelProperty("退货入库单状态, 1:编辑中, 2:待审核:3:已完成")
    private Integer inboundReturnOrderStatus;

    @ApiModelProperty("收货人")
    private String receiveName;

    @ApiModelProperty("收货地址")
    private String receiveAddress;

    @ApiModelProperty("收货人电话号码")
    private String receivePhone;

    @ApiModelProperty("用户支付运费")
    private BigDecimal freight;

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

    @ApiModelProperty("退货原因, 1:质量不好, 2:商品不满意, 3:买错了, 4:无理由退货")
    private Integer returnReason;

    @ApiModelProperty("退货备注")
    private String returnComment;

    @ApiModelProperty("到货时间")
    private LocalDateTime arrivalTime;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除,  0: 未删除")
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
