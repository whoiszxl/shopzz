package com.whoiszxl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/1/12
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MemberDTO对象", description="MemberDTO对象")
public class MemberDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "会员名")
    private String username;

    @ApiModelProperty(value = "谷歌验证码是否开启，默认0不开启, 1开启")
    private Integer googleStatus;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "状态(0：无效 1：有效)")
    private Integer status;

}
