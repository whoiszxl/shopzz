package com.whoiszxl.taowu.cqrs.query;

import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "属性值查询对象")
public class CategoryQuery extends PageQuery {

    @Schema(description = "分类父主键")
    private Long parentId;

    @Schema(description = "分类级别:1->1级 2->2级 3->3级")
    private Integer level;

}
