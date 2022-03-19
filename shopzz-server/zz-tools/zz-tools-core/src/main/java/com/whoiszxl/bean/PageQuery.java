package com.whoiszxl.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 分页查询对象
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="分页查询对象", description="分页查询对象")
public class PageQuery implements Serializable {

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "每页数量")
    private Integer size = 10;
}
