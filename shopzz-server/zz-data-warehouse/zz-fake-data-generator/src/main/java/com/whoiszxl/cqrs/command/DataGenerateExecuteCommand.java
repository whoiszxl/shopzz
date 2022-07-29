package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("数据生成执行命令")
public class DataGenerateExecuteCommand {

    @ApiModelProperty("需要生成数据的维度：member,order,")
    private String moduleName;

    @ApiModelProperty("生成批次间隔多少毫秒")
    private Integer interval;

    @ApiModelProperty("生成多少个批次")
    private Integer times;

    @ApiModelProperty("生成批次数量")
    private Integer quantity;

}
