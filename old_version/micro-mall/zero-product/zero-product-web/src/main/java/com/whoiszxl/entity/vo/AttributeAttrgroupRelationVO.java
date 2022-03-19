package com.whoiszxl.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 属性&属性分组关联表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_attribute_attrgroup_relation")
@ApiModel(value="AttributeAttrgroupRelation对象", description="属性&属性分组关联表")
public class AttributeAttrgroupRelationVO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性id")
    private Long attributeId;

    @ApiModelProperty(value = "属性分组id")
    private Long attributeGroupId;

}
