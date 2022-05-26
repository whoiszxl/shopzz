package com.whoiszxl.cqrs.command;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 短信批量发送命令
 *
 * @author whoiszxl
 * @date 2022/5/26
 */
@Data
@ApiModel("短信批量发送命令")
public class SmsBatchSendCommand {

    @ApiModelProperty("手机号集合")
    private List<String> mobile = new ArrayList<>();

    @ApiModelProperty("模板编码集合")
    private List<String> template = new ArrayList<>();

    @ApiModelProperty("签名编码集合")
    private List<String> signature = new ArrayList<>();

    @ApiModelProperty("参数集合")
    private List<LinkedHashMap<String, String>> params = new ArrayList<>();

    @ApiModelProperty("批次编码")
    private String batchCode;

    @ApiModelProperty("接入key")
    private String accessKeyId;

    @ApiModelProperty("认证值")
    private String encryption;

    @ApiModelProperty("发送时间戳")
    private String timestamp;

    @ApiModelProperty("定时时间 yyyy-MM-dd HH:mm")
    private String sendTime;
}
