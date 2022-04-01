package com.whoiszxl.db.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-01
 */
@Getter
@Setter
@TableName("ums_member")
@ApiModel(value = "Member对象", description = "会员表")
public class MemberPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("会员名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("支付密码")
    private String payPassword;

    @ApiModelProperty("谷歌验证码")
    private String googleKey;

    @ApiModelProperty("谷歌验证码是否开启,默认0不开启, 1开启")
    private Integer googleStatus;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("状态(0:无效 1:有效)")
    private Integer status;

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
