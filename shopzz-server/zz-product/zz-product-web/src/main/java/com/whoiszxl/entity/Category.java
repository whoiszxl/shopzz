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
 * 商品三级分类表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@TableName("pms_category")
@ApiModel(value = "Category对象", description = "商品三级分类表")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("父类目的主键")
    private Long parentId;

    @ApiModelProperty("分类级别:1->1级; 2->2级 3->3级")
    private Boolean level;

    @ApiModelProperty("是否显示[0-不显示,1显示]")
    private Boolean status;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("图标地址")
    private String icon;

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
