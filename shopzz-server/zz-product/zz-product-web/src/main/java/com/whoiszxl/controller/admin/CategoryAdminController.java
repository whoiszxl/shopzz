package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.query.CategoryQuery;
import com.whoiszxl.entity.AttributeKey;
import com.whoiszxl.entity.Category;
import com.whoiszxl.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品三级分类表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类后台相关接口")
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取分类列表", notes = "分页获取分类列表", response = Category.class)
    public ResponseResult<IPage<Category>> list(@RequestBody CategoryQuery query) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(query.getParentId() != null) {
            lambdaQueryWrapper.eq(Category::getParentId, query.getParentId());
        }
        if(query.getLevel() != null) {
            lambdaQueryWrapper.eq(Category::getLevel, query.getLevel());
        }

        Page<Category> result = categoryService.page(new Page<>(query.getPage(), query.getSize()), lambdaQueryWrapper);
        return ResponseResult.buildSuccess(result);
    }

}

