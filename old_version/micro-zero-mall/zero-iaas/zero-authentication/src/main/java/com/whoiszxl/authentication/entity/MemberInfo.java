package com.whoiszxl.authentication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 会员详情表(UmsMemberInfo)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "ums_member_info")
@Entity
public class MemberInfo implements Serializable {
    private static final long serialVersionUID = 686493267874799886L;
    /**
    * 用户ID
    */
    @Id
    private Integer memberId;
    /**
    * 性别(0:未知 1:男；2:女)
    */
    private Integer gender;
    /**
    * 生日
    */
    private Date birthday;
    /**
    * 国家码
    */
    private String countryCode;
    /**
    * 国家
    */
    private String country;
    /**
    * 省份
    */
    private String province;
    /**
    * 城市
    */
    private String city;
    /**
    * 区域
    */
    private String district;
    /**
    * 会员等级
    */
    private String gradeLevel;
    /**
    * 会员登录次数
    */
    private Integer loginCount;
    /**
    * 会员登录错误次数
    */
    private Integer loginErrorCount;
    /**
    * 最后登录
    */
    private Date lastLogin;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;

}