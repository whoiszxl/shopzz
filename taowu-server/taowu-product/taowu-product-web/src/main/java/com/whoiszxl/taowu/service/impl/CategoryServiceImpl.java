package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.command.CategorySaveCommand;
import com.whoiszxl.taowu.cqrs.command.CategoryUpdateCommand;
import com.whoiszxl.taowu.cqrs.response.CategoryApiResponse;
import com.whoiszxl.taowu.cqrs.response.CategoryOneLevelResponse;
import com.whoiszxl.taowu.cqrs.response.CategoryThreeLevelResponse;
import com.whoiszxl.taowu.cqrs.response.CategoryTwoLevelResponse;
import com.whoiszxl.taowu.entity.Category;
import com.whoiszxl.taowu.mapper.CategoryMapper;
import com.whoiszxl.taowu.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品三级分类表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final RedisUtils redisUtils;

    private final CategoryMapper categoryMapper;

    @Override
    public boolean removeNoChildCategoryById(Long id) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getParentId, id);
        long count = this.count(lambdaQueryWrapper);
        if (count != 0) {
            ExceptionCatcher.catchServiceEx("分类有下级，无法删除");
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
            ExceptionCatcher.catchServiceEx("同级下不允许分类名重复");
        }
        Category category = BeanUtil.copyProperties(categorySaveCommand, Category.class);
        return this.save(category);
    }

    @Override
    public boolean update(CategoryUpdateCommand categoryUpdateCommand) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getParentId, categoryUpdateCommand.getParentId());
        lambdaQueryWrapper.eq(Category::getName, categoryUpdateCommand.getName());
        long count = this.count(lambdaQueryWrapper);
        if (count != 0) {
            ExceptionCatcher.catchServiceEx("同级下不允许分类名重复");
        }
        Category category = BeanUtil.copyProperties(categoryUpdateCommand, Category.class);
        return this.updateById(category);
    }

    /**
     * 在所有分类中查询到categoryApiResponse的所有子分类
     * @param categoryApiResponse 需要查询子分类的父类
     * @param categoryApiResponseList 所有分类
     * @return
     */
    private List<CategoryApiResponse> getChildrens(CategoryApiResponse categoryApiResponse, List<CategoryApiResponse> categoryApiResponseList) {
        List<CategoryApiResponse> collect = categoryApiResponseList.stream().filter(item -> item.getParentId().equals(categoryApiResponse.getId()))
                .peek(menu -> menu.setChildren(getChildrens(menu, categoryApiResponseList)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return collect;
    }


    @Override
    public List<CategoryOneLevelResponse> oneLevel() {
        List<Category> categoryList = categoryMapper.selectList(Wrappers.<Category>lambdaQuery()
                .eq(Category::getLevel, 1)
                .orderByDesc(Category::getSort));

        List<CategoryOneLevelResponse> result = cn.hutool.core.bean.BeanUtil.copyToList(categoryList, CategoryOneLevelResponse.class);

        return result;
    }

    @Override
    public List<CategoryTwoLevelResponse> child(Long categoryId) {
        //1. 就是需要通过前端传过来的一级分类id，将其作为parent_id来查询第二级所有的分类。
        List<Category> categoryTwoLevelList = categoryMapper.selectList(Wrappers.<Category>lambdaQuery()
                .eq(Category::getParentId, categoryId)
                .orderByDesc(Category::getSort));

        List<CategoryTwoLevelResponse> categoryTwoLevelResponseList = cn.hutool.core.bean.BeanUtil.copyToList(categoryTwoLevelList, CategoryTwoLevelResponse.class);

        //2. 遍历填充第三级分类的列表
        for (CategoryTwoLevelResponse categoryTwoLevelResponse : categoryTwoLevelResponseList) {
            List<Category> categoryThreeLevelList = categoryMapper.selectList(Wrappers.<Category>lambdaQuery()
                    .eq(Category::getParentId, categoryTwoLevelResponse.getId())
                    .orderByDesc(Category::getSort));

            categoryTwoLevelResponse.setChildList(cn.hutool.core.bean.BeanUtil.copyToList(categoryThreeLevelList, CategoryThreeLevelResponse.class));
        }

        return categoryTwoLevelResponseList;
    }
}
