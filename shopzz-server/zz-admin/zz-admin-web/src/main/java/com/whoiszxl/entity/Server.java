package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 服务器表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("env_server")
@ApiModel(value="Server对象", description="服务器表")
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务器主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "服务实例ID")
    private String instanceId;

    @ApiModelProperty(value = "服务器host名称")
    private String serverName;

    @ApiModelProperty(value = "服务器外网ip地址")
    private String serverOuterIp;

    @ApiModelProperty(value = "服务器内网ip地址")
    private String serverInnerIp;

    @ApiModelProperty(value = "服务器端口")
    private String serverPort;

    @ApiModelProperty(value = "服务器用户名")
    private String serverUsername;

    @ApiModelProperty(value = "服务器密码")
    private String serverPassword;

    @ApiModelProperty(value = "服务器详情")
    private String serverDetail;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
