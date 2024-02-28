package com.whoiszxl.taowu.admin.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Schema(description = "管理员详情响应实体")
public class AdminDetailResponse {

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "账号")
    private String username;

    @Schema(description = "密码")
    private String password;

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

    @Schema(description = "谷歌验证码")
    private String googleCode;

    @Schema(description = "谷歌验证码是否开启，默认不开启 0-不开启； 1-开启")
    private Integer googleStatus;

    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "业务状态")
    private Integer status;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


    @Schema(description = "权限集合")
    private Set<String> permissions;

    @Schema(description = "角色集合")
    private Set<String> roles;

    @Schema(description = "角色ID集合")
    private Set<Integer> roleIds;
}
