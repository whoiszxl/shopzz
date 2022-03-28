package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@ApiModel(value = "Brand对象", description = "品牌表")
public class BrandUpdateCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("品牌主键id")
    private Long id;

    @ApiModelProperty("品牌中文名")
    private String chineseName;

    @ApiModelProperty("品牌英文名")
    private String englishName;

    @ApiModelProperty("品牌别名")
    private String aliasName;

    @ApiModelProperty("首字母")
    private String firstLetter;

    @ApiModelProperty("品牌logo地址")
    private String logo;

    @ApiModelProperty("品牌介绍")
    private String description;

    @ApiModelProperty("品牌授权图片")
    private String authPic;

    @ApiModelProperty("品牌所在地")
    private String location;

    @ApiModelProperty("显示状态[0-不显示; 1-显示]")
    private Integer showStatus;

    @ApiModelProperty("品牌说明备注")
    private String remark;

    @ApiModelProperty("排序")
    private Integer sort;

}
