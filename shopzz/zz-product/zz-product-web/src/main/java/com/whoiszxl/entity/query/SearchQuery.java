package com.whoiszxl.entity.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="商品搜索查询条件", description="商品搜索查询条件")
public class SearchQuery extends PageQuery {

    @ApiModelProperty("关键词，暂实现名称搜索")
    private String keywords;

    @ApiModelProperty("价格排序")
    private Integer priceSort;
}