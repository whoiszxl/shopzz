package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 商品专栏跟SPU关联表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Getter
@Setter
@ApiModel(value = "ProductColumnSpu对象", description = "商品专栏跟SPU关联表")
public class ProductColumnSpuSaveCommand implements Serializable {

    @ApiModelProperty("专栏主键ID")
    private Long columnId;

    @ApiModelProperty("SPU主键ID")
    private Long spuId;

}
