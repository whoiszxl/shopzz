package com.whoiszxl.zhipin.member.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-08
 */
@Data
@TableName("ums_member")
@Schema(description = "会员表")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "手机")
    private String phone;

    @Schema(description = "邮箱(选填)")
    private String email;

    @Schema(description = "登录密码")
    private String password;

    @Schema(description = "全名")
    private String fullName;

    @Schema(description = "参加工作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime workDate;

    @Schema(description = "微信号")
    private String wxCode;

    @Schema(description = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime birthday;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "区域")
    private String district;

    @Schema(description = "状态(1:男 2:女 3:未知)")
    private Integer gender;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "IP地址")
    private String ip;

    @Schema(description = "会员登录次数")
    private Long loginCount;

    @Schema(description = "会员登录错误次数")
    private Long loginErrorCount;

    @Schema(description = "最后登录")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastLogin;

    @Schema(description = "身份状态(1:职场人 2:学生)")
    private Integer identityStatus;

    @Schema(description = "求职状态(1:离职-随时到岗 2:在职-月内到岗 3:在职-考虑机会 4:在职-暂不考虑)")
    private Integer workStatus;

    @Schema(description = "最高学历(1:初中及以下 2:中专/中技 3:高中 4:大专 5:本科 6-硕士 7-博士)")
    private Integer highestQualification;

    @Schema(description = "最高学历类型(1:全日制 2:非全日制)")
    private Integer highestQualificationType;

    @Schema(description = "是否是头头(0:否 1:是)")
    private Integer isToutou;

    @Schema(description = "状态(0:无效 1:有效)")
    private Integer status;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;


}
