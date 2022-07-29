package com.whoiszxl.entity.common;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组件配置查询参数
 *
 * @author whoiszxl
 * @date 2021/8/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="组件配置查询参数", description="组件配置查询参数")
public class SoftwareConfigQuery extends PageQuery {

    @ApiModelProperty(value = "组件名称")
    private String softwareName;
}
