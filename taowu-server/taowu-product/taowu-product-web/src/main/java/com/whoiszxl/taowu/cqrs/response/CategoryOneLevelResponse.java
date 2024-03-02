package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "一级分类返回实体")
public class CategoryOneLevelResponse {

    @Schema(description = "分类id")
    private Long id;

    @Schema(description = "分类名称")
    private String name;
}
