package com.whoiszxl.authentication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.io.Serializable;

/**
 * 平台用户(SysUser)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "sys_user")
@Entity
public class SysUser implements Serializable {
    private static final long serialVersionUID = 212322780485407534L;
    /**
    * 主键
    */
    @Id
    private Long id;
    /**
    * 账号
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 姓名
    */
    private String fullname;
    /**
    * 手机号
    */
    private String mobile;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 谷歌验证码
    */
    private String googleCode;
    /**
    * 谷歌验证码是否开启，默认不开启 0-不开启； 1-开启
    */
    private Integer googleStatus;
    /**
    * 状态 0-无效； 1-有效；
    */
    private Integer status;
    /**
    * 创建人
    */
    private Long createdBy;
    /**
    * 更新人
    */
    private Long updatedBy;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;

}