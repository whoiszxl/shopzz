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
 * 区块高度同步记录
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dc_height")
@ApiModel(value="Height对象", description="区块高度同步记录")
public class Height implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "币种ID")
    @TableId(value = "currency_id", type = IdType.ID_WORKER)
    private Integer currencyId;

    @ApiModelProperty(value = "货币名称")
    private String currencyName;

    @ApiModelProperty(value = "当前服务扫描区块高度")
    private Long height;

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
