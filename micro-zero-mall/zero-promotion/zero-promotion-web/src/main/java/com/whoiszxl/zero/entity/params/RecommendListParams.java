package com.whoiszxl.zero.entity.params;

import com.whoiszxl.zero.bean.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecommendListParams extends PageParam {

    @ApiModelProperty("推荐商品类型 1:热门商品 2:精选商品")
    private Integer recommendType;

}
