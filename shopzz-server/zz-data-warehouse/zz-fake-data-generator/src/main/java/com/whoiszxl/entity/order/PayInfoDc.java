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
 * 数字货币支付信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Getter
@Setter
@TableName("oms_pay_info_dc")
@ApiModel(value = "DcPayInfo对象", description = "数字货币支付信息表")
public class PayInfoDc implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      private Long id;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("币种ID")
    private Integer currencyId;

    @ApiModelProperty("货币名称")
    private String currencyName;

    @ApiModelProperty("交易hash")
    private String txHash;

    @ApiModelProperty("订单总支付金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("用户的出币地址")
    private String fromAddress;

    @ApiModelProperty("关联的充值地址")
    private String toAddress;

    @ApiModelProperty("二维码数据")
    private String qrcodeData;

    @ApiModelProperty("上链时间")
    private LocalDateTime upchainAt;

    @ApiModelProperty("上链成功时间")
    private LocalDateTime upchainSuccessAt;

    @ApiModelProperty("上链状态，1：上链并确认成功 2：等待确认中 3：未上链")
    private Integer upchainStatus;

    @ApiModelProperty("当前交易确认数")
    private Long currentConfirm;

    @ApiModelProperty("当前交易所处区块的高度")
    private Long height;

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
