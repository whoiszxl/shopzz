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
 * 基础组件配置表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("env_software_config")
@ApiModel(value="SoftwareConfig对象", description="基础组件配置表")
public class SoftwareConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组件配置文件主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "组件文件名")
    private String softwareName;

    @ApiModelProperty(value = "配置文件名")
    private String configName;

    @ApiModelProperty(value = "配置文件路径")
    private String configPath;

    @ApiModelProperty(value = "配置文件模板")
    private String configTemplate;

    @ApiModelProperty(value = "配置文件模板参数，JSON格式保存，example: {'namenode_port':10000}")
    private String configTemplateParams;

    @ApiModelProperty(value = "安装状态：1 未安装 2 安装成功")
    private Integer installStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
