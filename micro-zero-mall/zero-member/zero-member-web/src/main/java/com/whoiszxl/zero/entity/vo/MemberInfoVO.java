package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 会员详情表(UmsMemberInfo)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@ApiModel("会员详情vo")
@Data
public class MemberInfoVO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 686493267874799886L;


    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("头像")
    private String profilePhoto;

    @ApiModelProperty("性别(0:未知 1:男；2:女)")
    private Integer gender;

    @ApiModelProperty("生日")
    private Date birthday;

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
    private Integer loginCount;

    @ApiModelProperty("会员登录错误次数")
    private Integer loginErrorCount;

    @ApiModelProperty("最后登录")
    private Date lastLogin;

}