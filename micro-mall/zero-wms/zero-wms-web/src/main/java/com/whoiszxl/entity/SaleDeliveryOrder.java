package com.whoiszxl.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 销售出库订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_sale_delivery_order")
@ApiModel(value="SaleDeliveryOrder对象", description="销售出库订单表")
public class SaleDeliveryOrder extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户账号ID")
    private Long memberId;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "收货人")
    private String receiveName;

    @ApiModelProperty(value = "收货地址")
    private String receiveAddress;

    @ApiModelProperty(value = "收货人电话号码")
    private String receivePhone;

    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    @ApiModelProperty(value = "支付方式: [1:支付宝 2:微信 3:数字货币]")
    private Integer payType;

    @ApiModelProperty(value = "订单总额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "运费金额")
    private BigDecimal freightAmount;

    @ApiModelProperty(value = "促销折扣金额")
    private BigDecimal promotionAmount;

    @ApiModelProperty(value = "积分抵扣金额")
    private BigDecimal pointAmount;

    @ApiModelProperty(value = "优惠券抵扣金额")
    private BigDecimal couponAmount;

    @ApiModelProperty(value = "后台调整订单使用的折扣金额")
    private BigDecimal adminDiscountAmount;

    @ApiModelProperty(value = "应付总额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "发票类型[0->不开发票；1->电子发票；2->纸质发票]")
    private Integer invoiceType;

    @ApiModelProperty(value = "发票抬头")
    private String invoiceHeader;

    @ApiModelProperty(value = "纳税人识别号")
    private String taxpayerId;

    @ApiModelProperty(value = "订单备注")
    private String orderComment;

    @ApiModelProperty(value = "销售出库单的状态，1：编辑中，2：待审核，3：已完成")
    private Integer saleDeliveryOrderStatus;

    @ApiModelProperty(value = "发货时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
