package com.whoiszxl.zero.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页请求实体
 *
 * @author whoiszxl
 * @date 2021/3/25
 */
@Data
public class PageParam {

    @ApiModelProperty("页码")
    private Integer pageNumber = 0;

    @ApiModelProperty("每页数量")
    private Integer pageSize = 10;

}
