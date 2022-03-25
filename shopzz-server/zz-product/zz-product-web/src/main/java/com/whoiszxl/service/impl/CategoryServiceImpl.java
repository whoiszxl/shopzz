package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.CategorySaveCommand;
import com.whoiszxl.cqrs.command.CategoryUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Category;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.mapper.CategoryMapper;
import com.whoiszxl.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品三级分类表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public boolean removeNoChildCategoryById(Long id) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getParentId, id);
        long count = this.count(lambdaQueryWrapper);
        if (count != 0) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("分类有下级，无法删除"));
        }

        return this.removeById(id);
    }

    @Override
    public boolean save(CategorySaveCommand categorySaveCommand) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getParentId, categorySaveCommand.getParentId());
        lambdaQueryWrapper.eq(Category::getName, categorySaveCommand.getName());
        long count = this.count(lambdaQueryWrapper);
        if (count != 0) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("同级下不允许分类名重复"));
        }
        Category category = dozerUtils.map(categorySaveCommand, Category.class);
        return this.save(category);
    }

    @Override
    public boolean update(CategoryUpdateCommand categoryUpdateCommand) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getParentId, categoryUpdateCommand.getParentId());
        lambdaQueryWrapper.eq(Category::getName, categoryUpdateCommand.getName());
        long count = this.count(lambdaQueryWrapper);
        if (count != 0) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("同级下不允许分类名重复"));
        }
        Category category = dozerUtils.map(categoryUpdateCommand, Category.class);
        return this.updateById(category);
    }
}
