package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpuSaveCommand implements Serializable {

    @NotBlank(message = "商品名称不能为空")
    @Schema(description = "商品名称")
    private String name;

    @NotBlank(message = "商品副名称不能为空")
    @Schema(description = "商品副名称")
    private String subName;

    @NotNull
    @DecimalMax(value = "999999.99", message = "商品售价不可高于999999.99元")
    @DecimalMin(value = "00.01", message = "商品售价不可低于0.01元")
    @Schema(description = "默认价格")
    private BigDecimal defaultPrice;

    @Schema(description = "商品默认图片地址")
    private String defaultPic;

    @NotNull(message = "类目ID不能为空")
    @Schema(description = "类目ID")
    private Long categoryId;

    @Schema(description = "父类目ID")
    private Long parentCategoryId;

    @Schema(description = "品牌ID")
    private Long brandId;

    @Schema(description = "品牌名称")
    private String brandName;

    @Schema(description = "上架状态:0->下架; 1->上架")
    private Integer publishStatus;

    @Schema(description = "包装清单")
    private String packageList;

    @Schema(description = "默认选中的SKU ID")
    private Long defaultSkuId;

    @Schema(description = "SPU 轮播图集合")
    private List<String> spuImageList;

    @Schema(description = "商详数据")
    private String detailHtml;

    @Schema(description = "属性键 ID集合")
    private List<Long> attributeKeyIdList;
}
