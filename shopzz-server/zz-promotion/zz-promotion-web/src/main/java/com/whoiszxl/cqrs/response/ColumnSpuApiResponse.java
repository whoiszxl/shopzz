package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品基础信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@ApiModel(value = "Spu对象", description = "商品基础信息表")
public class ColumnSpuApiResponse implements Serializable {

    @ApiModelProperty("商品id")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品副名称")
    private String subName;

    @ApiModelProperty("默认价格")
    private BigDecimal defaultPrice;

    @ApiModelProperty("商品默认图片地址")
    private String defaultPic;

}
