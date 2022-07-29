package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 基础组件表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("env_software")
@ApiModel(value="Software对象", description="基础组件表")
public class Software implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组件主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "组件名")
    private String softwareName;

    @ApiModelProperty(value = "组件文件名")
    private String softwareFilename;

    @ApiModelProperty(value = "组件文件路径")
    private String softwarePath;

    @ApiModelProperty(value = "组件安装路径")
    private String installPath;

    @ApiModelProperty(value = "环境变量路径")
    private String envPath;

    @ApiModelProperty(value = "环境变量内容")
    private String envContent;

    @ApiModelProperty(value = "安装状态：1 未安装 2 部分安装 3 全安装")
    private Integer installStatus;

    @ApiModelProperty(value = "安装了此组件的服务ID集合， 逗号分隔 : 1,2,3")
    private String installServerIds;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
