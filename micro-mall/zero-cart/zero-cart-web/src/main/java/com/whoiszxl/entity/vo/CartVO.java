package com.whoiszxl.entity.vo;

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
 * 购物车记录表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Cart对象", description="购物车记录表")
public class CartVO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "购物车主键ID")
    private Long id;

    @ApiModelProperty(value = "购物车所属用户ID")
    private Long memberId;

    @ApiModelProperty(value = "商品SPU ID")
    private Long productId;

    @ApiModelProperty(value = "商品SKU ID")
    private Long skuId;

    @ApiModelProperty(value = "SKU名称")
    private String skuName;

    @ApiModelProperty(value = "SKU图片")
    private String skuPic;

    @ApiModelProperty(value = "购买数量")
    private Integer quantity;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "销售属性")
    private String saleAttr;

    @ApiModelProperty(value = "是否选中 0未选中 1选中")
    private Boolean checked;

    @ApiModelProperty(value = "状态：0失效 1有效")
    private Boolean status;

}
