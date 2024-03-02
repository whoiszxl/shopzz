package com.whoiszxl.taowu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * sku信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@Schema(description = "sku信息表")
public class SkuFeignDTO implements Serializable {

    @Schema(description = "sku主键ID")
    private Long id;

    @Schema(description = "商品SPU的ID")
    private Long spuId;

    @Schema(description = "所属分类id")
    private Long categoryId;

    @Schema(description = "父类目ID")
    private Long parentCategoryId;

    @Schema(description = "sku名称")
    private String skuName;

    @Schema(description = "sku缩略图片地址")
    private String skuImg;

    @Schema(description = "销售价格")
    private BigDecimal salePrice;

    @Schema(description = "促销价格")
    private BigDecimal promotionPrice;

    @Schema(description = "商品销售属性,json格式")
    private String saleAttr;

    @Schema(description = "SKU编码")
    private String skuCode;

    @Schema(description = "乐观锁")
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除, 0: 未删除")
    private Integer isDeleted;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;


}
