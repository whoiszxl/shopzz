package com.whoiszxl.cqrs.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("属性值查询对象")
public class CategoryQuery extends PageQuery {

    @ApiModelProperty("分类父主键")
    private Long parentId;

    @ApiModelProperty("分类级别:1->1级 2->2级 3->3级")
    private Integer level;

}
