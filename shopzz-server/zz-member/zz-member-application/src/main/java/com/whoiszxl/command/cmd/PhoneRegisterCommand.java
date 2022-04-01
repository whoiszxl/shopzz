package com.whoiszxl.command.cmd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 手机号注册指令
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Data
@ApiModel("手机号注册指令")
public class PhoneRegisterCommand {

    @NotBlank(message = "用户名不允许为空")
    @ApiModelProperty("用户名")
    private String username;

    @NotBlank(message = "密码不允许为空")
    @ApiModelProperty("密码")
    private String password;
}
