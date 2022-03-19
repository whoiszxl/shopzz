package com.whoiszxl.zero.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户权限配置(SysRolePrivilegeUser)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "sys_role_privilege_user")
@Entity
public class SysRolePrivilegeUser implements Serializable {
    private static final long serialVersionUID = -49747561347655954L;

    @Id
    private Long id;
    /**
    * 角色Id
    */
    private Long roleId;
    /**
    * 用户Id
    */
    private Long userId;
    /**
    * 权限Id
    */
    private Long privilegeId;

}