package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * sku信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@ApiModel(value = "Sku对象", description = "sku信息表")
public class SkuSaveCommand implements Serializable {

    @ApiModelProperty("商品SPU的ID")
    private Long spuId;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("sku缩略图片地址")
    private String skuImg;

    @ApiModelProperty("销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty("促销价格")
    private BigDecimal promotionPrice;

    @ApiModelProperty("商品销售属性,json格式")
    private String saleAttr;

    @ApiModelProperty("SKU编码")
    private String skuCode;

    @NotNull
    @ApiModelProperty("SKU编码")
    private List<SkuAttribute> skuAttributeList;

    @Data
    @ApiModel(value = "Sku属性对象", description = "Sku属性对象")
    public class SkuAttribute {

        @ApiModelProperty("属性键ID")
        private Long keyId;

        @ApiModelProperty("属性键名称")
        private String keyName;

        @ApiModelProperty("属性值ID")
        private Long valueId;

        @ApiModelProperty("属性值名称")
        private String valueName;
    }
}
