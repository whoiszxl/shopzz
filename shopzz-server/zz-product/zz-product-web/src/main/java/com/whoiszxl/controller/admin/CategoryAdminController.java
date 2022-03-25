package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.AttributeKeySaveCommand;
import com.whoiszxl.cqrs.command.AttributeKeyUpdateCommand;
import com.whoiszxl.cqrs.command.CategorySaveCommand;
import com.whoiszxl.cqrs.command.CategoryUpdateCommand;
import com.whoiszxl.cqrs.query.CategoryQuery;
import com.whoiszxl.entity.AttributeKey;
import com.whoiszxl.entity.Category;
import com.whoiszxl.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增分类", notes = "新增分类", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody @Validated CategorySaveCommand categorySaveCommand) {
        boolean saveFlag = categoryService.save(categorySaveCommand);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新分类", notes = "更新分类", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody CategoryUpdateCommand categoryUpdateCommand) {
        boolean updateFlag = categoryService.update(categoryUpdateCommand);
        return ResponseResult.buildByFlag(updateFlag);
    }


    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除无下级的分类", notes = "删除无下级的分类", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = categoryService.removeNoChildCategoryById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

