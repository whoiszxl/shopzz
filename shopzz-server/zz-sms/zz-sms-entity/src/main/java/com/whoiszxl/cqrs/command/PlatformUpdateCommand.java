package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 短信平台表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Data
@ApiModel(value = "Platform对象", description = "短信平台表")
public class PlatformUpdateCommand implements Serializable {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("平台名称")
    private String name;

    @ApiModelProperty("秘钥id")
    private String accessKeyId;

    @ApiModelProperty("秘钥密码")
    private String accessKeySecret;

    @ApiModelProperty("IP绑定,多个以逗号分隔")
    private String ipAddress;

    @ApiModelProperty("是否需要鉴权: 0-不需要 1-需要")
    private Integer needAuth;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("状态(0:无效 1:有效)")
    private Integer status;

}
