package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 会员详情表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_info")
@ApiModel(value="MemberInfo对象", description="会员详情表")
public class MemberInfo extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "member_id", type = IdType.ID_WORKER)
    private Long memberId;

    @ApiModelProperty(value = "头像")
    private String profilePhoto;

    @ApiModelProperty(value = "性别(0:未知 1:男；2:女)")
    private Integer gender;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "国家码")
    private String countryCode;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区域")
    private String district;

    @ApiModelProperty(value = "会员等级")
    private String gradeLevel;

    @ApiModelProperty(value = "会员登录次数")
    private Integer loginCount;

    @ApiModelProperty(value = "会员登录错误次数")
    private Integer loginErrorCount;

    @ApiModelProperty(value = "最后登录")
    private Date lastLogin;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;

}
