package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author whoiszxl
 * @since 2021-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_admin_opt_log")
@ApiModel(value="AdminOptLog对象", description="系统日志")
public class AdminOptLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作IP")
    private String requestIp;

    @ApiModelProperty(value = "日志类型 #LogType{OPT:操作类型;EX:异常类型}")
    private String type;

    @ApiModelProperty(value = "操作人")
    private String adminName;

    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "类路径")
    private String classPath;

    @ApiModelProperty(value = "请求方法")
    private String actionMethod;

    @ApiModelProperty(value = "请求地址")
    private String requestUri;

    @ApiModelProperty(value = "请求类型#HttpMethod{GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}")
    private String httpMethod;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "返回值")
    private String result;

    @ApiModelProperty(value = "异常详情信息")
    private String exDesc;

    @ApiModelProperty(value = "异常描述")
    private String exDetail;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

    @ApiModelProperty(value = "消耗时间")
    private Long consumingTime;

    @ApiModelProperty(value = "浏览器")
    private String ua;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "业务状态")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
