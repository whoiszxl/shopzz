package com.whoiszxl.entity.product;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@TableName("pms_spu")
@ApiModel(value = "Spu对象", description = "商品基础信息表")
public class Spu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品副名称")
    private String subName;

    @ApiModelProperty("默认价格")
    private BigDecimal defaultPrice;

    @ApiModelProperty("商品默认图片地址")
    private String defaultPic;

    @ApiModelProperty("类目ID")
    private Long categoryId;

    @ApiModelProperty("父类目ID")
    private Long parentCategoryId;

    @ApiModelProperty("品牌ID")
    private Long brandId;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("删除状态:0->未删除; 1->已删除")
    private Integer deleteStatus;

    @ApiModelProperty("上架状态:0->下架; 1->上架")
    private Integer publishStatus;

    @ApiModelProperty("审核状态:0->未审核; 1->审核通过")
    private Integer verifyStatus;

    @ApiModelProperty("包装清单")
    private String packageList;

    @ApiModelProperty("默认选中的SKU ID")
    private Long defaultSkuId;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;


}
