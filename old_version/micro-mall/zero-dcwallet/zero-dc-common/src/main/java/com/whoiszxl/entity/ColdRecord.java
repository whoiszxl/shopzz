package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="ColdRecord对象", description="转冷钱包记录")
public class ColdRecord extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "币种ID")
    private Integer currencyId;

    @ApiModelProperty(value = "货币名称")
    private String currencyName;

    @ApiModelProperty(value = "转冷金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "转冷钱包交易hash")
    private String txHash;

    @ApiModelProperty(value = "业务系统中交易或其他绑定的地址")
    private String fromAddress;

    @ApiModelProperty(value = "冷钱包地址")
    private String toAddress;

    @ApiModelProperty(value = "上链时间")
    private Date upchainAt;

    @ApiModelProperty(value = "上链成功时间")
    private Date upchainSuccessAt;

    @ApiModelProperty(value = "上链状态，1：成功 2：失败 3：上链后等待确认中")
    private Integer upchainStatus;

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
