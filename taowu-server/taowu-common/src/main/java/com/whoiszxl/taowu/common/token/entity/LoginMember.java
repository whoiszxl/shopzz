package com.whoiszxl.taowu.common.token.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 登录用户的实体
 * @author whoiszxl
 */
@Data
public class LoginMember {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "账号")
    private String username;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "性别（0未知 1男 2女）")
    private Integer gender;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "谷歌验证码是否开启，默认不开启 0-不开启； 1-开启")
    private Integer googleStatus;

    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "令牌")
    private String token;

    @Schema(description = "登录ip地址")
    private String ip;

    @Schema(description = "登录地点")
    private String location;

    @Schema(description = "浏览器信息")
    private String browser;

    @Schema(description = "权限集合")
    private Set<String> permissions;

    @Schema(description = "角色集合")
    private Set<String> roles;
}
