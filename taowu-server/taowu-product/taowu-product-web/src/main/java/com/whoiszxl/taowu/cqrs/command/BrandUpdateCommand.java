package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
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
@Data
@Schema(description = "品牌表")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandUpdateCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "品牌主键id")
    private Long id;

    @Schema(description = "品牌中文名")
    private String chineseName;

    @Schema(description = "品牌英文名")
    private String englishName;

    @Schema(description = "品牌别名")
    private String aliasName;

    @Schema(description = "首字母")
    private String firstLetter;

    @Schema(description = "品牌logo地址")
    private String logo;

    @Schema(description = "品牌介绍")
    private String description;

    @Schema(description = "品牌授权图片")
    private String authPic;

    @Schema(description = "品牌所在地")
    private String location;

    @Schema(description = "显示状态[0-不显示; 1-显示]")
    private Integer showStatus;

    @Schema(description = "品牌说明备注")
    private String remark;

    @Schema(description = "排序")
    private Integer sort;

}
