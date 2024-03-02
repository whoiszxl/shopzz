package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 区块高度同步记录
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dc_height")
@Schema(description = "区块高度同步记录")
public class Height implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "币种ID")
    @TableId(value = "currency_id")
    private Integer currencyId;

    @Schema(description = "货币名称")
    private String currencyName;

    @Schema(description = "当前服务扫描区块高度")
    private Long currentHeight;

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
