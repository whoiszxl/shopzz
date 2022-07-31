package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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


    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
