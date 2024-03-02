package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 加密货币表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dc_currency")
@Schema(description = "加密货币表")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "币种ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "货币名称")
    private String currencyName;

    @Schema(description = "货币logo")
    private String currencyLogo;

    @Schema(description = "货币类型： mainnet：主网币 token：代币")
    private String currencyType;

    @Schema(description = "币种描述")
    private String currencyContent;

    @Schema(description = "币种小数位")
    private Integer currencyDecimalsNum;

    @Schema(description = "该币种的链接地址")
    private String currencyUrl;

    @Schema(description = "智能合约abi接口")
    private String contractAbi;

    @Schema(description = "智能合约地址")
    private String contractAddress;

    @Schema(description = "冷钱包地址")
    private String coldAddress;

    @Schema(description = "转冷钱包阈值")
    private BigDecimal coldThreshold;

    @Schema(description = "提币手续费")
    private BigDecimal feeWithdraw;

    @Schema(description = "钱包密钥")
    private String walletKey;

    @Schema(description = "充值确认数")
    private Integer confirms;

    @Schema(description = "币种状态，0：关闭 1：开启")
    private Integer status;

    @Schema(description = "乐观锁")
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建者")
    private String createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新者")
    private String updatedBy;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private Date updatedAt;

}
