package com.whoiszxl.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_brand")
@ApiModel(value="Brand对象", description="品牌表")
public class BrandVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "品牌主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "品牌中文名")
    private String chineseName;

    @ApiModelProperty(value = "品牌英文名")
    private String englishName;

    @ApiModelProperty(value = "品牌别名")
    private String aliasName;

    @ApiModelProperty(value = "首字母")
    private String firstLetter;

    @ApiModelProperty(value = "品牌logo地址")
    private String logo;

    @ApiModelProperty(value = "品牌介绍")
    private String description;

    @ApiModelProperty(value = "品牌授权图片")
    private String authPic;

    @ApiModelProperty(value = "品牌所在地")
    private String location;

    @ApiModelProperty(value = "显示状态[0-不显示；1-显示]")
    private Integer showStatus;

    @ApiModelProperty(value = "品牌说明备注")
    private String remark;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
