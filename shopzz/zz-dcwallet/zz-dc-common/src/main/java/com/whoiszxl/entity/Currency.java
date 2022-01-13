package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Currency对象", description="加密货币表")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "币种ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "货币名称")
    private String currencyName;

    @ApiModelProperty(value = "货币logo")
    private String currencyLogo;

    @ApiModelProperty(value = "货币类型： mainnet：主网币 token：代币")
    private String currencyType;

    @ApiModelProperty(value = "币种描述")
    private String currencyContent;

    @ApiModelProperty(value = "币种小数位")
    private Integer currencyDecimalsNum;

    @ApiModelProperty(value = "该币种的链接地址")
    private String currencyUrl;

    @ApiModelProperty(value = "智能合约abi接口")
    private String contractAbi;

    @ApiModelProperty(value = "智能合约地址")
    private String contractAddress;

    @ApiModelProperty(value = "冷钱包地址")
    private String coldAddress;

    @ApiModelProperty(value = "转冷钱包阈值")
    private BigDecimal coldThreshold;

    @ApiModelProperty(value = "提币手续费")
    private BigDecimal feeWithdraw;

    @ApiModelProperty(value = "钱包密钥")
    private String walletKey;

    @ApiModelProperty(value = "充值确认数")
    private Integer confirms;

    @ApiModelProperty(value = "币种状态，0：关闭 1：开启")
    private Integer status;

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
