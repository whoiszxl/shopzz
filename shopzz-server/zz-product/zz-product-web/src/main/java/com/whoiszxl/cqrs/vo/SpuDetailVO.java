package com.whoiszxl.cqrs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 商品详情页数据表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@ApiModel(value = "SpuDetail VO对象", description = "SpuDetail VO")
public class SpuDetailVO implements Serializable {

    @ApiModelProperty("PC商品详情富文本内容")
    private String detailHtml;

    @ApiModelProperty("移动端商品详情富文本内容")
    private String detailMobile;


}
