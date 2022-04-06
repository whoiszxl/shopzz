package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "api:首页推荐返回实体", description = "首页推荐返回实体")
public class HomeRecommendVO implements Serializable {

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
