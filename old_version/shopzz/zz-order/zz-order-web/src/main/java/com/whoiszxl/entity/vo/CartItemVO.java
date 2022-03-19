package com.whoiszxl.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemVO {

    @ApiModelProperty("购物车主键ID")
    private Long id;

    @ApiModelProperty("商品SPU ID")
    private Long productId;

    @ApiModelProperty("商品SKU ID")
    private Long skuId;

    @ApiModelProperty("SKU名称")
    private String skuName;

    @ApiModelProperty("SKU图片")
    private String skuPic;

    @ApiModelProperty("购买数量")
    private Integer quantity;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("是否选中 0未选中 1选中")
    private Integer checked;
}
