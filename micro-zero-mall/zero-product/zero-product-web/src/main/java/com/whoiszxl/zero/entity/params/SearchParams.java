package com.whoiszxl.zero.entity.params;

import com.whoiszxl.zero.bean.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchParams extends PageParam {

    @ApiModelProperty("关键词，暂实现名称搜索")
    private String keywords;

    @ApiModelProperty("价格排序")
    private Integer priceSort;
}
