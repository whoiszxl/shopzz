package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "二级分类返回实体")
public class CategoryTwoLevelResponse {

    @Schema(description = "分类id")
    private Long id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "第三级分类列表")
    private List<CategoryThreeLevelResponse> childList;
}
