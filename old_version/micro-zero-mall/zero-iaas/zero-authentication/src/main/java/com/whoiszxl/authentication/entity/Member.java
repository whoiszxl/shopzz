package com.whoiszxl.authentication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 会员表(UmsMember)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "ums_member")
@Entity
public class Member implements Serializable {
    private static final long serialVersionUID = 990936872328330808L;
    /**
    * 主键ID
    */
    @Id
    private Long id;
    /**
    * 会员名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 支付密码
    */
    private String payPassword;
    /**
    * 谷歌验证码
    */
    private String googleKey;
    /**
    * 谷歌验证码是否开启，默认0不开启, 1开启
    */
    private Integer googleStatus;
    /**
    * 真实姓名
    */
    private String realName;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 手机
    */
    private String phone;
    /**
    * 状态(0：无效 1：有效)
    */
    private Integer status;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;
}