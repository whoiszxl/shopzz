package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@TableName("pms_brand")
@ApiModel(value = "Brand对象", description = "品牌表")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("品牌主键id")
      @TableId(value = "id", type = IdType.AUTO)
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

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
