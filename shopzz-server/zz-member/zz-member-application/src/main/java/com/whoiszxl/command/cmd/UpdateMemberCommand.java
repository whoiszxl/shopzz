package com.whoiszxl.command.cmd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 更新会员信息指令
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Data
@ApiModel("更新会员信息指令")
public class UpdateMemberCommand {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("性别(0:未知 1:男；2:女)")
    private Integer gender;

    @ApiModelProperty("生日")
    private LocalDateTime birthday;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区域")
    private String district;

    @ApiModelProperty("学校")
    private String school;

}
