package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 表动态分流处理表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-07-31
 */
@Getter
@Setter
@TableName("env_table_process")
@ApiModel(value = "TableProcess对象", description = "表动态分流处理表")
public class TableProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("脚本文件主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("来源表")
    private String sourceTable;

    @ApiModelProperty("操作类型:insert,update,delete")
    private String operateType;

    @ApiModelProperty("输出类型:hbase,kafka")
    private String sinkType;

    @ApiModelProperty("输出表或topic")
    private String sinkTable;

    @ApiModelProperty("输出字段")
    private String sinkColumns;

    @ApiModelProperty("主键字段")
    private String sinkPk;

    @ApiModelProperty("建表扩展")
    private String sinkExtend;


}
