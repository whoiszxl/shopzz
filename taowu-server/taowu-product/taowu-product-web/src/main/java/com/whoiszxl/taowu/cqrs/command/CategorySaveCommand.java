package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品三级分类表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@Schema(description = "商品三级分类表")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategorySaveCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "父类目的主键")
    private Long parentId;

    @Schema(description = "分类级别:1->1级; 2->2级 3->3级")
    private Integer level;

    @Schema(description = "是否显示[0-不显示,1显示]")
    private Integer status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "图标地址")
    private String icon;

}
