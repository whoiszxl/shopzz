package com.whoiszxl.aggregate.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员详情聚合根
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-01
 */
@Data
@ApiModel(value = "MemberInfo聚合根", description = "会员详情聚合根")
public class MemberInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
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


    public void bindMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
