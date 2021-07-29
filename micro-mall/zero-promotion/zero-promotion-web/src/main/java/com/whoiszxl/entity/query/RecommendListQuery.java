package com.whoiszxl.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecommendListQuery {

    @ApiModelProperty("推荐商品类型 1:热门商品 2:精选商品")
    private Integer recommendType;

}
