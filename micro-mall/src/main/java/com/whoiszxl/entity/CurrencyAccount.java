package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@TableName("dc_currency_account")
@ApiModel(value="CurrencyAccount对象", description="账号管理表")
public class CurrencyAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "币种ID")
    @TableId(value = "currency_id", type = IdType.ID_WORKER)
    private Integer currencyId;

    @ApiModelProperty(value = "货币名称")
    private String currencyName;

    @ApiModelProperty(value = "keystore文件名")
    private String keystoreName;

    @ApiModelProperty(value = "助记词")
    private String mnemonic;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
