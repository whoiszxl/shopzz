package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "首页推荐返回实体")
public class HomeRecommendApiResponse implements Serializable {

    @Schema(description = "推荐SPU列表")
    private List<HomeRecommendVO> homeRecommendList;

}
