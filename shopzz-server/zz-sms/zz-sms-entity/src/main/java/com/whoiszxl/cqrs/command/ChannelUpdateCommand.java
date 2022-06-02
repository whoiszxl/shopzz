package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 短信通道表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Data
@ApiModel(value = "Channel对象", description = "短信通道表")
public class ChannelUpdateCommand implements Serializable {

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

    @ApiModelProperty("其他配置")
    private String otherConfig;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("类型: 1-文字 2-语音 3-推送")
    private Integer channelType;

    @ApiModelProperty("状态(0:无效 1:有效)")
    private Integer status;

}
