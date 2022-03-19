package com.whoiszxl.authentication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统菜单(SysMenu)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "sys_menu")
@Entity
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 279068999910644326L;
    /**
    * 主键
    */
    @Id
    private Long id;
    /**
    * 上级菜单ID
    */
    private Long parentId;
    /**
    * 上级菜单唯一KEY值
    */
    private String parentKey;
    /**
    * 类型 1-分类 2-节点
    */
    private Integer type;
    /**
    * 名称
    */
    private String name;
    /**
    * 描述
    */
    private String desc;
    /**
    * 目标地址
    */
    private String targetUrl;
    /**
    * 排序索引
    */
    private Integer sort;
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