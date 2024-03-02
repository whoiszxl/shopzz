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
 * 转冷钱包记录
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dc_cold_record")
@Schema(description = "转冷钱包记录")
public class ColdRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "币种ID")
    private Integer currencyId;

    @Schema(description = "货币名称")
    private String currencyName;

    @Schema(description = "转冷金额")
    private BigDecimal amount;

    @Schema(description = "转冷钱包交易hash")
    private String txHash;

    @Schema(description = "业务系统中交易或其他绑定的地址")
    private String fromAddress;

    @Schema(description = "冷钱包地址")
    private String toAddress;

    @Schema(description = "上链时间")
    private Date upchainAt;

    @Schema(description = "上链成功时间")
    private Date upchainSuccessAt;

    @Schema(description = "上链状态，1：成功 2：失败 3：上链后等待确认中")
    private Integer upchainStatus;

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
