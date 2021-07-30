package com.whoiszxl.dto;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * sku信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Sku对象", description="sku信息表")
public class SkuDTO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku主键ID")
    private Long id;

    @ApiModelProperty(value = "商品SPU的ID")
    private Long productId;

    @ApiModelProperty(value = "所属分类id")
    private Long categoryId;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "sku缩略图片地址")
    private String imgUrl;

    @ApiModelProperty(value = "采购价格")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "促销价格")
    private BigDecimal promotionPrice;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "商品销售属性，json格式")
    private String saleData;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
