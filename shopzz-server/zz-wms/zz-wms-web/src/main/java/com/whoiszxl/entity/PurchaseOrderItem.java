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
 * 采购订单商品详情表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Getter
@Setter
@TableName("wms_purchase_order_item")
@ApiModel(value = "PurchaseOrderItem对象", description = "采购订单商品详情表")
public class PurchaseOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("采购单ID")
    private Long purchaseOrderId;

    @ApiModelProperty("商品SKU ID")
    private Long skuId;

    @ApiModelProperty("采购数量")
    private Long purchaseQuantity;

    @ApiModelProperty("采购价格")
    private BigDecimal purchasePrice;

    @ApiModelProperty("合格商品的数量")
    private Integer qualifiedCount;

    @ApiModelProperty("到货的商品数量")
    private Integer arrivalCount;

    @ApiModelProperty("仓库ID")
    private Long warehouseId;

    @ApiModelProperty("仓库货架ID")
    private Long warehouseShelfId;

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
