package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
@Data
@TableName("oms_pay_info_dc")
@Schema(description = "数字货币支付信息表")
public class PayInfoDc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "币种ID")
    private Integer currencyId;

    @Schema(description = "货币名称")
    private String currencyName;

    @Schema(description = "交易hash")
    private String txHash;

    @Schema(description = "订单总支付金额")
    private BigDecimal totalAmount;

    @Schema(description = "用户的出币地址")
    private String fromAddress;

    @Schema(description = "关联的充值地址")
    private String toAddress;

    @Schema(description = "二维码数据")
    private String qrcodeData;

    @Schema(description = "上链时间")
    private LocalDateTime upchainAt;

    @Schema(description = "上链成功时间")
    private LocalDateTime upchainSuccessAt;

    @Schema(description = "上链状态，1：上链并确认成功 2：等待确认中 3：未上链")
    private Integer upchainStatus;

    @Schema(description = "当前交易确认数")
    private Long currentConfirm;

    @Schema(description = "当前交易所处区块的高度")
    private Long height;

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
