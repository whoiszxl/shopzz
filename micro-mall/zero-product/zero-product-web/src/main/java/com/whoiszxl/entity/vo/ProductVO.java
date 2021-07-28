package com.whoiszxl.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品基础信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_product")
@ApiModel(value="Product对象", description="商品基础信息表")
public class ProductVO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品副名称")
    private String subName;

    @ApiModelProperty(value = "默认价格")
    private BigDecimal defaultPrice;

    @ApiModelProperty(value = "商品默认图片地址")
    private String defaultPic;

    @ApiModelProperty(value = "类目ID")
    private Long categoryId;

    @ApiModelProperty(value = "品牌ID")
    private Long brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "商品毛重，单位:g")
    private BigDecimal grossWeight;

    @ApiModelProperty(value = "外包装长度，单位:cm")
    private BigDecimal length;

    @ApiModelProperty(value = "外包装宽，单位:cm")
    private BigDecimal width;

    @ApiModelProperty(value = "外包装高，单位:cm")
    private BigDecimal height;

    @ApiModelProperty(value = "服务保证，多标语逗号隔开")
    private String serviceGuarantees;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private Boolean deleteStatus;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Boolean publishStatus;

    @ApiModelProperty(value = "审核状态：0->未审核；1->审核通过")
    private Boolean verifyStatus;

    @ApiModelProperty(value = "包装清单")
    private String packageList;

    @ApiModelProperty(value = "运费模板ID")
    private Long freightTemplateId;

}
