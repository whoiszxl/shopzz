package com.whoiszxl.cqrs.command;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "Blacklist对象", description = "短信人工处理任务表")
public class BlacklistSaveCommand implements Serializable {

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态")
    private Integer status;

}
