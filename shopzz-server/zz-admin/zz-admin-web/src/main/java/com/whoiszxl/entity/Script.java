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
 * SH脚本表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("env_script")
@ApiModel(value="Script对象", description="SH脚本表")
public class Script implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "脚本文件主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "脚本名称")
    private String scriptName;

    @ApiModelProperty(value = "脚本路径")
    private String scriptPath;

    @ApiModelProperty(value = "脚本内容")
    private String scriptContent;

    @ApiModelProperty(value = "脚本描述")
    private String scriptDesc;

    @ApiModelProperty(value = "状态：1 未同步 2 已同步")
    private Boolean status;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
