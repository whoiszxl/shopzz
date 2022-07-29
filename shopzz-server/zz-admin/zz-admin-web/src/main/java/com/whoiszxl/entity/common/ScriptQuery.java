package com.whoiszxl.entity.common;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 脚本查询参数
 *
 * @author whoiszxl
 * @date 2021/8/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="脚本查询参数", description="脚本查询参数")
public class ScriptQuery extends PageQuery {

    @ApiModelProperty(value = "脚本名称")
    private String scriptName;
}
