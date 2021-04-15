package com.whoiszxl.zero.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MyPage<T> {

    @ApiModelProperty("分页数据内容")
    private List<T> content;

    @ApiModelProperty("总页数")
    private Integer totalPages;

    @ApiModelProperty("页码")
    private Integer number;

    @ApiModelProperty("每页数")
    private Integer size;

    @ApiModelProperty("是否为首页")
    private boolean first;

}
