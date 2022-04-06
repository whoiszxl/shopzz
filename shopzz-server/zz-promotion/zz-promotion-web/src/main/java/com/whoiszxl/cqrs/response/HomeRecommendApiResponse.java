package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "api:首页推荐返回实体", description = "首页推荐返回实体")
public class HomeRecommendApiResponse implements Serializable {

    @ApiModelProperty("推荐SPU列表")
    private List<HomeRecommendVO> homeRecommendList;

}
