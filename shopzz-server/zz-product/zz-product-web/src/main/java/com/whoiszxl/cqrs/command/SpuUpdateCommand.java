package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
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
@Getter
@Setter
@ApiModel(value = "Spu对象", description = "商品基础信息表")
public class SpuUpdateCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    private Long id;

    @NotBlank(message = "商品名称不能为空")
    @ApiModelProperty("商品名称")
    private String name;

    @NotBlank(message = "商品副名称不能为空")
    @ApiModelProperty("商品副名称")
    private String subName;

    @NotNull
    @DecimalMax(value = "999999.99", message = "商品售价不可高于999999.99元")
    @DecimalMin(value = "00.01", message = "商品售价不可低于0.01元")
    @ApiModelProperty("默认价格")
    private BigDecimal defaultPrice;

    @ApiModelProperty("商品默认图片地址")
    private String defaultPic;

    @NotNull(message = "类目ID不能为空")
    @ApiModelProperty("类目ID")
    private Long categoryId;

    @NotNull(message = "父类目ID不能为空")
    @ApiModelProperty("父类目ID")
    private Long parentCategoryId;

    @ApiModelProperty("品牌ID")
    private Long brandId;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("上架状态:0->下架; 1->上架")
    private Integer publishStatus;

    @ApiModelProperty("包装清单")
    private String packageList;

    @ApiModelProperty("默认选中的SKU ID")
    private Long defaultSkuId;

    @NotEmpty
    @ApiModelProperty("SPU 轮播图集合")
    private List<String> spuImageList;

    @NotEmpty
    @ApiModelProperty("商详数据")
    private String detailHtml;

    @NotEmpty
    @ApiModelProperty("属性键 ID集合")
    private List<Long> attributeKeyIdList;


}
