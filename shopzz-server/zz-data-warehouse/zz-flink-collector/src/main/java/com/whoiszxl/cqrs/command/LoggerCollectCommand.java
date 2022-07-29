package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("日志收集命令")
public class LoggerCollectCommand {

    @ApiModelProperty("JSON数据")
    private String data;
}
