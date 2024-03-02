package com.whoiszxl.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "购物车item vo")
public class CartItemVO {

    @Schema(description = "购物车主键ID")
    private Long id;

    @Schema(description = "购物车所属用户ID")
    private Long memberId;

    @Schema(description = "商品SPU ID")
    private Long productId;

    @Schema(description = "商品SKU ID")
    private Long skuId;

    @Schema(description = "SKU名称")
    private String skuName;

    @Schema(description = "SKU图片")
    private String skuPic;

    @Schema(description = "购买数量")
    private Integer quantity;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "销售属性")
    private String saleAttr;

    @Schema(description = "是否选中 0未选中 1选中")
    private Integer checked;

    @Schema(description = "状态：0失效 1有效")
    private Integer status;
}
