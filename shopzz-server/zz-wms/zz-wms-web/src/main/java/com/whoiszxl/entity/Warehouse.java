package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * WMS仓库表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Getter
@Setter
@TableName("wms_warehouse")
@ApiModel(value = "Warehouse对象", description = "WMS仓库表")
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库类型: 1-自营仓")
    private Integer warehouseType;

    @ApiModelProperty("仓库地址")
    private String warehouseAddress;

    @ApiModelProperty("仓库管理员姓名")
    private String warehouseAdminName;

    @ApiModelProperty("仓库管理员联系电话")
    private String warehouseAdminPhone;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除,  0: 未删除")
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
