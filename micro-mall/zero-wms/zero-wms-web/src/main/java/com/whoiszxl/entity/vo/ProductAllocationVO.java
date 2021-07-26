package com.whoiszxl.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 货位表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProductAllocation对象", description="货位表")
public class ProductAllocationVO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "货位编号")
    private String productAllocationCode;

    @ApiModelProperty(value = "说明备注")
    private String productAllocationComment;

}
