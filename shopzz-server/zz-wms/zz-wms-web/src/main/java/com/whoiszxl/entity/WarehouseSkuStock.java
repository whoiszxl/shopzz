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
 * WMS仓库商品库存表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Getter
@Setter
@TableName("wms_warehouse_sku_stock")
@ApiModel(value = "WarehouseSkuStock对象", description = "WMS仓库商品库存表")
public class WarehouseSkuStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商品SKU ID")
    private Long skuId;

    @ApiModelProperty("商品SKU名称")
    private String skuName;

    @ApiModelProperty("可用库存数量")
    private Integer availableStockQuantity;

    @ApiModelProperty("锁定库存数量")
    private Integer lockedStockQuantity;

    @ApiModelProperty("预警库存数量")
    private Integer warnStockQuantity;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除,  0: 未删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
