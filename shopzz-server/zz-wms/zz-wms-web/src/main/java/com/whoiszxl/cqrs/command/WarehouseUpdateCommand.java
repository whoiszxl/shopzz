package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * WMS仓库表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Data
@ApiModel(value = "Warehouse对象", description = "WMS仓库表")
public class WarehouseUpdateCommand implements Serializable {


    @ApiModelProperty("主键")
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
}
