package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Category对象", description = "商品三级分类表")
public class CategorySaveCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("父类目的主键")
    private Long parentId;

    @ApiModelProperty("分类级别:1->1级; 2->2级 3->3级")
    private Integer level;

    @ApiModelProperty("是否显示[0-不显示,1显示]")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("图标地址")
    private String icon;

}
