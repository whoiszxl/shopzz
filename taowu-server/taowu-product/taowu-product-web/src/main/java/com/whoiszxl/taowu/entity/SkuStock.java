package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品SKU库存表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@TableName("pms_sku_stock")
@Schema(description = "商品SKU库存表")
public class SkuStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "商品sku ID")
    private Long skuId;

    @Schema(description = "销售库存")
    private Integer saleStockQuantity;

    @Schema(description = "锁定库存")
    private Integer lockedStockQuantity;

    @Schema(description = "已销售库存")
    private Integer saledStockQuantity;

    @Schema(description = "库存状态,0:无库存,1:有库存")
    private Integer stockStatus;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
