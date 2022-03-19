package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "Warehouse返回对象", description = "Warehouse返回对象")
public class WarehouseResponse implements Serializable {


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

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
