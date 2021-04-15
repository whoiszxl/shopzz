package com.whoiszxl.zero.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色菜单(SysRoleMenu)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "sys_role_menu")
@Entity
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = -87466453588329587L;

    @Id
    private Long id;
    
    private Long roleId;
    
    private Long menuId;
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