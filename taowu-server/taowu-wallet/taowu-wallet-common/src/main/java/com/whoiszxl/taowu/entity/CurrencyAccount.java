package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 账号管理表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dc_currency_account")
@Schema(description = "账号管理表")
public class CurrencyAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "币种ID")
    private Integer currencyId;

    @Schema(description = "货币名称")
    private String currencyName;

    @Schema(description = "keystore文件名")
    private String keystoreName;

    @Schema(description = "助记词")
    private String mnemonic;

    @Schema(description = "地址")
    private String address;

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
