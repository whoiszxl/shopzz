package com.whoiszxl.cqrs.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("属性值查询对象")
public class AttributeValueQuery extends PageQuery {

    @ApiModelProperty("属性键ID")
    private Long keyId;
}
