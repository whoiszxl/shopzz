package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemVO {

    @ApiModelProperty("购物车主键ID")
    private Long id;

    @ApiModelProperty("购物车所属用户ID")
    private Long memberId;

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

    @ApiModelProperty("销售属性")
    private String saleAttr;

    @ApiModelProperty("是否选中 0未选中 1选中")
    private Integer checked;

    @ApiModelProperty("状态：0失效 1有效")
    private Integer status;
}
