package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.query.CategoryQuery;
import com.whoiszxl.cqrs.response.CategoryApiResponse;
import com.whoiszxl.cqrs.response.CategoryListTreeApiResponse;
import com.whoiszxl.entity.Category;
import com.whoiszxl.service.CategoryService;
import com.whoiszxl.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "C端:分类相关接口")
public class CategoryApiController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list/tree")
    @ApiOperation(value = "以树状形式返回所有分类", notes = "以树状形式返回所有分类", response = CategoryListTreeApiResponse.class)
    public ResponseResult<CategoryListTreeApiResponse> listTree() {
        CategoryListTreeApiResponse categoryApiResponses = categoryService.listWithTree();
        return ResponseResult.buildSuccess(categoryApiResponses);
    }

}

