package com.whoiszxl.taowu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Version;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品基础信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@Schema(description = "商品基础信息表")
public class SpuFeignDTO implements Serializable {

    @Schema(description = "商品id")
    private Long id;

    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "商品副名称")
    private String subName;

    @Schema(description = "默认价格")
    private BigDecimal defaultPrice;

    @Schema(description = "商品默认图片地址")
    private String defaultPic;

    @Schema(description = "类目ID")
    private Long categoryId;

    @Schema(description = "父类目ID")
    private Long parentCategoryId;

    @Schema(description = "品牌ID")
    private Long brandId;

    @Schema(description = "品牌名称")
    private String brandName;

    @Schema(description = "删除状态:0->未删除; 1->已删除")
    private Integer deleteStatus;

    @Schema(description = "上架状态:0->下架; 1->上架")
    private Integer publishStatus;

    @Schema(description = "审核状态:0->未审核; 1->审核通过")
    private Integer verifyStatus;

    @Schema(description = "包装清单")
    private String packageList;

    @Schema(description = "默认选中的SKU ID")
    private Long defaultSkuId;

    @Schema(description = "乐观锁")
    @Version
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
