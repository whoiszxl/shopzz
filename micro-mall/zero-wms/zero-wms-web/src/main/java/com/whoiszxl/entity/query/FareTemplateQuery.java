package com.whoiszxl.entity.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 运费模板查询对象
 *
 * @author whoiszxl
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="运费模板查询对象", description="运费模板查询对象")
public class FareTemplateQuery extends PageQuery {

    @ApiModelProperty(value = "模板名称")
    private String templateName;

    @ApiModelProperty(value = "运费模板类型，1：固定运费，2：满X元包邮，3：自定义规则")
    private Integer templateType;
}
