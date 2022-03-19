package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员表(UmsMember)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@ApiModel("会员基础信息VO")
@Data
public class MemberVO  extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 990936872328330808L;

    @ApiModelProperty("主键用户ID")
    private Long id;

    @ApiModelProperty("会员名")
    private String username;

    @ApiModelProperty("谷歌验证码是否开启，默认0不开启, 1开启")
    private Integer googleStatus;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("状态(0：无效 1：有效)")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createdAt;
}