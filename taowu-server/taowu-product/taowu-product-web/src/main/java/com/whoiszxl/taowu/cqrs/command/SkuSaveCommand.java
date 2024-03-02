package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Data
@Schema(description = "sku信息表")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuSaveCommand implements Serializable {

    @Schema(description = "商品SPU的ID")
    private Long spuId;

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

    @NotNull
    @Schema(description = "SKU编码")
    private List<SkuAttribute> skuAttributeList;

    @Data
    @Schema(description = "Sku属性对象")
    public class SkuAttribute {

        @Schema(description = "属性键ID")
        private Long keyId;

        @Schema(description = "属性键名称")
        private String keyName;

        @Schema(description = "属性值ID")
        private Long valueId;

        @Schema(description = "属性值名称")
        private String valueName;
    }
}
