package com.whoiszxl.authentication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.io.Serializable;

/**
 * 角色(SysRole)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "sys_role")
@Entity
public class SysRole implements Serializable {
    private static final long serialVersionUID = 836928732816583690L;
    /**
    * 主键
    */
    @Id
    private Long id;
    /**
    * 名称
    */
    private String name;
    /**
    * 代码
    */
    private String code;
    /**
    * 描述
    */
    private String description;
    /**
    * 创建人
    */
    private Long createdBy;
    /**
    * 更新人
    */
    private Long updatedBy;
    /**
    * 状态0:禁用 1:启用
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