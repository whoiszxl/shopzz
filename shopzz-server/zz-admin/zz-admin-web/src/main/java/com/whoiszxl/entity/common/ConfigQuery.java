package com.whoiszxl.entity.common;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置查询参数
 *
 * @author whoiszxl
 * @date 2021/8/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="配置查询参数", description="配置查询参数")
public class ConfigQuery extends PageQuery {

    @ApiModelProperty(value = "配置名称")
    private String key;
}
