package com.whoiszxl.authentication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户角色配置(SysUserRole)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "sys_user_role")
@Entity
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 401918777816613429L;
    /**
    * 主键
    */
    @Id
    private Long id;
    /**
    * 角色ID
    */
    private Long roleId;
    /**
    * 用户ID
    */
    private Long userId;
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