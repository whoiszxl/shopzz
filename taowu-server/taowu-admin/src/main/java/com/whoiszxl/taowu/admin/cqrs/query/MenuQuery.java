package com.whoiszxl.taowu.admin.cqrs.query;

import com.whoiszxl.taowu.common.annotation.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author whoiszxl
 */
@Data
@ParameterObject
@Schema(description = "菜单查询条件")
public class MenuQuery {

    @Schema(description = "菜单名称")
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

}
