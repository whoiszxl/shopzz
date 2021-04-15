package com.whoiszxl.zero.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志(SysUserLog)实体类
 *
 * @author whoiszxl
 * @since 2021-04-08
 */
@Data
@Table(name = "sys_user_log")
@Entity
public class SysUserLog implements Serializable {
    private static final long serialVersionUID = 516853453649357974L;
    /**
    * 主键
    */
    @Id
    private Long id;
    /**
    * 组
    */
    private String group;
    /**
    * 用户Id
    */
    private Long userId;
    /**
    * 日志类型 1查询 2修改 3新增 4删除 5导出 6审核
    */
    private Integer type;
    /**
    * 方法
    */
    private String method;
    /**
    * 参数
    */
    private String params;
    /**
    * 时间
    */
    private Long time;
    /**
    * IP地址
    */
    private String ip;
    /**
    * 描述
    */
    private String description;
    /**
    * 备注
    */
    private String remark;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;
}