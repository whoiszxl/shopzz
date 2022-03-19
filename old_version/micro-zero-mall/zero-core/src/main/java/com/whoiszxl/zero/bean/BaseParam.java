package com.whoiszxl.zero.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("基础请求参数")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BaseParam {

    @ApiModelProperty("主键ID")
    private Long id;
}
