package com.whoiszxl.entity.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("登录接口参数")
public class InstallQuery {

    @ApiModelProperty("组件名称")
    private String softwareName;

    @ApiModelProperty("服务器ID集合")
    private List<Integer> serverIds;
}

