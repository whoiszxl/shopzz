package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分类树api返回实体")
public class CategoryListTreeApiResponse {

    @Schema(description = "分类树列表")
    private List<CategoryApiResponse> categorys;
}
