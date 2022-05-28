package com.whoiszxl.cqrs.command;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 短信发送命令
 *
 * @author whoiszxl
 * @date 2022/5/26
 */
@Data
@ApiModel("短信发送命令")
public class SmsSendCommand {

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("模板编码")
    private String template;

    @ApiModelProperty("签名编码")
    private String signature;

    @ApiModelProperty("参数")
    private Map<String, String> params;

    @ApiModelProperty("接入key")
    private String accessKeyId;

    @ApiModelProperty("认证值")
    private String encryption;

    @ApiModelProperty("发送时间戳")
    private String timestamp;

    @ApiModelProperty("定时时间 yyyy-MM-dd HH:mm")
    private String sendTime;

    @ApiModelProperty("批次编码,仅批量发送用")
    private String batchCode;
}
