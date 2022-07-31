package com.whoiszxl.entity.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("ECS生成参数")
public class EcsGenerateQuery {

    @ApiModelProperty("平台 aliyun")
    private String platform;

    @ApiModelProperty("生成台数")
    private Integer quantity;

    @ApiModelProperty("实例名称")
    private String instanceName;

    @ApiModelProperty("主机名称")
    private String hostName;

    @ApiModelProperty("密码")
    private String password;

}

