package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分类树api返回实体")
public class CategoryListTreeApiResponse {

    @ApiModelProperty("分类树列表")
    private List<CategoryApiResponse> categorys;
}
