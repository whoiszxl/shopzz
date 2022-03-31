package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.cqrs.command.CategorySaveCommand;
import com.whoiszxl.cqrs.command.CategoryUpdateCommand;
import com.whoiszxl.cqrs.response.CategoryApiResponse;
import com.whoiszxl.cqrs.response.CategoryListTreeApiResponse;
import com.whoiszxl.entity.Category;

import java.util.List;

/**
 * <p>
 * 商品三级分类表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
public interface CategoryService extends IService<Category> {

    /**
     * 通过主键ID删除无下级的分类
     * @param id
     * @return
     */
    boolean removeNoChildCategoryById(Long id);

    /**
     * 新增分类
     * @param categorySaveCommand
     * @return
     */
    boolean save(CategorySaveCommand categorySaveCommand);

    /**
     * 更新分类
     * @param categoryUpdateCommand
     * @return
     */
    boolean update(CategoryUpdateCommand categoryUpdateCommand);

    /**
     * 获取分类树
     * @return
     */
    CategoryListTreeApiResponse listWithTree();

}
