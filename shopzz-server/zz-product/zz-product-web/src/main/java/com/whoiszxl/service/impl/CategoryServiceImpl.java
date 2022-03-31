package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.command.CategorySaveCommand;
import com.whoiszxl.cqrs.command.CategoryUpdateCommand;
import com.whoiszxl.cqrs.response.CategoryApiResponse;
import com.whoiszxl.cqrs.response.CategoryListTreeApiResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Category;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.mapper.CategoryMapper;
import com.whoiszxl.service.CategoryService;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private RedisUtils redisUtils;

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

    @Override
    public CategoryListTreeApiResponse listWithTree() {
        String redisKey = RedisKeyPrefixConstants.CATEGORY_TREE;
        String categoryTreeJson = redisUtils.get(redisKey);

        if(StringUtils.isBlank(categoryTreeJson)) {

            synchronized (this) {
                categoryTreeJson = redisUtils.get(redisKey);
                if(StringUtils.isBlank(categoryTreeJson)) {
                    CategoryListTreeApiResponse response = new CategoryListTreeApiResponse();
                    //1. 先查询到所有的分类
                    List<Category> categories = super.list(null);
                    List<CategoryApiResponse> categoryApiResponseList = dozerUtils.mapList(categories, CategoryApiResponse.class);

                    //2. 过滤出一级分类，然后再遍历查询一级分类item的子分类，直到查询到最后一级，通过sort字段进行排序
                    List<CategoryApiResponse> collect = categoryApiResponseList.stream().filter(item -> item.getParentId() == 0)
                            .peek(menu -> menu.setChildren(getChildrens(menu, categoryApiResponseList)))
                            .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                            .collect(Collectors.toList());

                    response.setCategorys(collect);

                    categoryTreeJson = JsonUtil.toJson(response);
                    redisUtils.set(redisKey, categoryTreeJson);
                }
            }
        }
        return JsonUtil.fromJson(categoryTreeJson, CategoryListTreeApiResponse.class);
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
}
