package com.whoiszxl.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("禁用or解禁管理员请求参数")
public class LockQuery {

    @ApiModelProperty("-1: 禁用  1: 解禁")
    private Integer type;
}
