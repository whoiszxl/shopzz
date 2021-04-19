package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车表
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@ApiModel("购物车vo")
@Data
public class CartVO extends AbstractObject implements Serializable {

    @ApiModelProperty("主键ID")
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

    @ApiModelProperty("状态")
    private Integer status;
}