package com.whoiszxl.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单退货表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_order_return_apply")
@ApiModel(value="OrderReturnApply对象", description="订单退货表")
public class OrderReturnApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单明细项ID")
    private Long orderItemId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "订单中SKU的ID")
    private Long skuId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "运费")
    private BigDecimal freight;

    @ApiModelProperty(value = "退货数量")
    private Integer returnCount;

    @ApiModelProperty(value = "退货原因，1：质量不好，2：商品不满意，3：买错了，4：无理由退货")
    private Integer returnReason;

    @ApiModelProperty(value = "退货备注")
    private String returnComment;

    @ApiModelProperty(value = "退货图片备注，逗号分割")
    private String returnPic;

    @ApiModelProperty(value = "退货记录状态，1：待审核，2：审核不通过，3：审核通过")
    private Integer returnApplyStatus;

    @ApiModelProperty(value = "退货快递单号")
    private String returnLogisticCode;

    @ApiModelProperty(value = "收货人")
    private String returnReceiveName;

    @ApiModelProperty(value = "收货备注")
    private String returnReceiveNote;

    @ApiModelProperty(value = "收货电话")
    private String returnReceivePhone;

    @ApiModelProperty(value = "公司收货地址")
    private String returnCompanyAddress;

    @ApiModelProperty(value = "收货时间")
    private Date returnReceiveTime;

    @ApiModelProperty(value = "处理备注")
    private String handleNote;

    @ApiModelProperty(value = "处理人员")
    private String handleBy;

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
