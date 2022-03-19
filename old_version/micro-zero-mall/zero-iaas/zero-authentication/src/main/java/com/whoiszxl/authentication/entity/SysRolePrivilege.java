package com.whoiszxl.authentication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色权限配置(SysRolePrivilege)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "sys_role_privilege")
@Entity
public class SysRolePrivilege implements Serializable {
    private static final long serialVersionUID = 455356282905297378L;

    @Id
    private Long id;
    
    private Long roleId;
    
    private Long privilegeId;

}