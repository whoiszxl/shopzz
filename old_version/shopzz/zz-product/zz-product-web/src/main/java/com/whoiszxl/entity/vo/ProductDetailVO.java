package com.whoiszxl.entity.vo;

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
 * 商品详情页数据表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_product_detail")
@ApiModel(value="ProductDetail对象", description="商品详情页数据表")
public class ProductDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品ID")
    @TableId(value = "product_id")
    private Long productId;

    @ApiModelProperty(value = "PC商品详情富文本内容")
    private String detailHtml;

    @ApiModelProperty(value = "移动端商品详情富文本内容")
    private String detailMobile;

}
