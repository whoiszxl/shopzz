package com.whoiszxl.cqrs.command;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@ApiModel(value = "Sku对象", description = "sku信息表")
public class SkuSaveCommand implements Serializable {

    @ApiModelProperty("商品SPU的ID")
    private Long spuId;

    @ApiModelProperty("sku名称")
    private Long skuName;

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

}
