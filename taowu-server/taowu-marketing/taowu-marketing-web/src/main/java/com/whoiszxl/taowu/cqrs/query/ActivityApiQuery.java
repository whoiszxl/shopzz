package com.whoiszxl.taowu.cqrs.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "活动api查询对象")
public class ActivityApiQuery {

    @NotNull(message = "活动ID不允许为空")
    @Schema(description = "活动ID")
    private Long id;

}
