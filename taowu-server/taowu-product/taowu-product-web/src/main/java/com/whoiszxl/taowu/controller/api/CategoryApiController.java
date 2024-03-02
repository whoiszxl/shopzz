package com.whoiszxl.taowu.controller.api;


import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.response.CategoryOneLevelResponse;
import com.whoiszxl.taowu.cqrs.response.CategoryTwoLevelResponse;
import com.whoiszxl.taowu.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品三级分类表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/api/category")
@Tag(name = "C端:分类相关接口")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;


    @GetMapping("/one/level")
    @Operation(description = "获取所有第一级分类的列表")
    public ResponseResult<List<CategoryOneLevelResponse>> oneLevel() {
        List<CategoryOneLevelResponse> result = categoryService.oneLevel();
        return ResponseResult.buildSuccess(result);
    }

    @GetMapping("/child/{categoryId}")
    @Operation(description = "获取第一级分类下的二三级分类列表")
    public ResponseResult<List<CategoryTwoLevelResponse>> child(@PathVariable Long categoryId) {
        List<CategoryTwoLevelResponse> result = categoryService.child(categoryId);
        return ResponseResult.buildSuccess(result);
    }

}

