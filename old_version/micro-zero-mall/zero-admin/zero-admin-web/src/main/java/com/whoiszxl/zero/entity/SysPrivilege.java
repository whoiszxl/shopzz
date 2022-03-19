package com.whoiszxl.zero.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 权限配置(SysPrivilege)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "sys_privilege")
@Entity
public class SysPrivilege implements Serializable {
    private static final long serialVersionUID = -45201552807401281L;
    /**
    * 主键
    */
    @Id
    private Long id;
    /**
    * 所属菜单Id
    */
    private Long menuId;
    /**
    * 功能点名称
    */
    private String name;
    /**
    * 功能描述
    */
    private String description;
    
    private String url;
    
    private String method;
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