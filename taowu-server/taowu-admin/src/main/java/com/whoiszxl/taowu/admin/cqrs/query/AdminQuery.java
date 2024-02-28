package com.whoiszxl.taowu.admin.cqrs.query;

import com.whoiszxl.taowu.common.annotation.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author whoiszxl
 */
@Data
@ParameterObject
@Schema(description = "管理员查询条件")
public class AdminQuery {

    @Schema(description = "用户名")
    @Query(blurry = "username,real_name,mobile,email")
    private String username;

    @Schema(description = "状态")
    @Query
    private Integer status;

    @Schema(description = "创建时间区间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN, property = "created_at")
    private List<Date> createdAt;
}
