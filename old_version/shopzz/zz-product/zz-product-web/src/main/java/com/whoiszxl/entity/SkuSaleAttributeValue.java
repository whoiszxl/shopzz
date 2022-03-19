package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * sku销售属性值表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_sku_sale_attribute_value")
@ApiModel(value="SkuSaleAttributeValue对象", description="sku销售属性值表")
public class SkuSaleAttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "sku_id")
    private Long skuId;

    @ApiModelProperty(value = "attribute_id")
    private Long attributeId;

    @ApiModelProperty(value = "销售属性名")
    private String attributeName;

    @ApiModelProperty(value = "销售属性值")
    private String attributeValue;

    @ApiModelProperty(value = "顺序")
    private Integer attributeSort;


}
