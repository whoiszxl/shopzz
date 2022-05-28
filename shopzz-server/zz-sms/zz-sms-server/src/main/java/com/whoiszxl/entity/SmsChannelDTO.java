package com.whoiszxl.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * SMS 通道DTO
 *
 * @author whoiszxl
 * @date 2022/5/28
 */
@Data
public class SmsChannelDTO {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("平台")
    private String platform;

    @ApiModelProperty("域名")
    private String domain;

    @ApiModelProperty("秘钥id")
    private String accessKeyId;

    @ApiModelProperty("秘钥密码")
    private String accessKeySecret;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("类型: 1-文字 2-语音 3-推送")
    private Integer channelType;

    @ApiModelProperty("状态(0:无效 1:有效)")
    private Integer status;

    @ApiModelProperty("其他配置")
    private LinkedHashMap<String, String> otherConfig = new LinkedHashMap<>();

    public String getOtherConfigValue(String key) {
        return otherConfig.get(key);
    }

    public void setOtherConfigValue(String key, String value) {
        otherConfig.put(key, value);
    }
}
