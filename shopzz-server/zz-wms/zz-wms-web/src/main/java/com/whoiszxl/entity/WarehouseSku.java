package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * WMS仓库SKU表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Getter
@Setter
@TableName("wms_warehouse_sku")
@ApiModel(value = "WarehouseSku对象", description = "WMS仓库SKU表")
public class WarehouseSku implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("货架ID")
    private Long shelfId;

    @ApiModelProperty("商品SKU ID")
    private Long skuId;

    @ApiModelProperty("商品sku编号")
    private String skuCode;

    @ApiModelProperty("商品SKU名称")
    private String skuName;

    @ApiModelProperty("商品采购价格")
    private BigDecimal purchasePrice;

    @ApiModelProperty("商品长度")
    private BigDecimal length;

    @ApiModelProperty("商品宽度")
    private BigDecimal width;

    @ApiModelProperty("商品高度")
    private BigDecimal height;

    @ApiModelProperty("商品毛重")
    private BigDecimal grossWeight;

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
