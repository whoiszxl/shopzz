package com.whoiszxl.entity.member;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员详情表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-01
 */
@Getter
@Setter
@TableName("ums_member_info")
@ApiModel(value = "MemberInfo对象", description = "会员详情表")
public class MemberInfoPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("性别(0:未知 1:男; 2:女)")
    private Integer gender;

    @ApiModelProperty("生日")
    private LocalDateTime birthday;

    @ApiModelProperty("国家码")
    private String countryCode;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区域")
    private String district;

    @ApiModelProperty("会员等级")
    private String gradeLevel;

    @ApiModelProperty("会员登录次数")
    private Long loginCount;

    @ApiModelProperty("会员登录错误次数")
    private Long loginErrorCount;

    @ApiModelProperty("最后登录")
    private LocalDateTime lastLogin;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
