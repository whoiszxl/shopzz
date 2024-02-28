package com.whoiszxl.taowu.admin.cqrs.query;

import com.whoiszxl.taowu.common.annotation.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @author whoiszxl
 */
@Data
@ParameterObject
@Schema(description = "角色查询条件")
public class RoleQuery {

    @Schema(description = "角色名称")
    @Query
    private String name;

    @Schema(description = "状态")
    @Query
    private Integer status;

}
