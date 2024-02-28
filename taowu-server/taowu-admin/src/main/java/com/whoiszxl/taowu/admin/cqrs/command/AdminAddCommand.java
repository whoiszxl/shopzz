package com.whoiszxl.taowu.admin.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author whoiszxl
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "管理员新增命令")
public class AdminAddCommand {

    @Schema(description = "账号")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "头像")
    private String avatar = "https://shopzz.oss-cn-guangzhou.aliyuncs.com/other/a1.jpg";

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "性别（0未知 1男 2女）")
    private Integer gender;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "谷歌验证码")
    private String googleCode;

    @Schema(description = "谷歌验证码是否开启，默认不开启 0-不开启； 1-开启")
    private Integer googleStatus;

    @Schema(description = "所属角色")
    @NotEmpty(message = "所属角色不能为空")
    private List<Integer> roleIds;
}