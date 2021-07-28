package com.whoiszxl.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_attribute")
@ApiModel(value="Attribute对象", description="商品属性")
public class AttributeVO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "属性名称")
    private String attributeName;

    @ApiModelProperty(value = "属性图标地址")
    private String icon;

    @ApiModelProperty(value = "是否需要检索[0-不需要，1-需要]")
    private Integer searchType;

    @ApiModelProperty(value = "值类型[0-为单个值，1-可以选择多个值]")
    private Integer valueType;

    @ApiModelProperty(value = "可选值列表[用逗号分隔]")
    private String valueSelect;

    @ApiModelProperty(value = "属性类型[0-销售属性，1-基本属性]")
    private Boolean attributeType;

    @ApiModelProperty(value = "启用状态[0 - 禁用，1 - 启用]")
    private Boolean status;


}
