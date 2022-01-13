package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 数字货币支付信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_pay_info_dc")
@ApiModel(value="DcPayInfo对象", description="数字货币支付信息表")
public class DcPayInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "币种ID")
    private Integer currencyId;

    @ApiModelProperty(value = "货币名称")
    private String currencyName;

    @ApiModelProperty(value = "交易hash")
    private String txHash;

    @ApiModelProperty(value = "订单总支付金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "用户的出币地址")
    private String fromAddress;

    @ApiModelProperty(value = "关联的充值地址")
    private String toAddress;

    @ApiModelProperty(value = "二维码数据")
    private String qrcodeData;

    @ApiModelProperty(value = "上链时间")
    private Date upchainAt;

    @ApiModelProperty(value = "上链成功时间")
    private Date upchainSuccessAt;

    @ApiModelProperty(value = "上链状态，1：上链并确认成功 2：等待确认中 3：未上链")
    private Integer upchainStatus;

    @ApiModelProperty(value = "当前交易确认数")
    private Long currentConfirm;

    @ApiModelProperty(value = "当前交易所处区块的高度")
    private Long height;

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
