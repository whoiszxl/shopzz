package com.whoiszxl.taowu.cqrs.query;

import com.whoiszxl.taowu.common.annotation.Query;
import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "属性值查询对象")
public class AttributeValueQuery extends PageQuery {

    @Query
    @Schema(description = "属性键ID")
    private Long keyId;
}
