package com.whoiszxl.taowu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.cqrs.command.CategorySaveCommand;
import com.whoiszxl.taowu.cqrs.command.CategoryUpdateCommand;
import com.whoiszxl.taowu.cqrs.response.CategoryOneLevelResponse;
import com.whoiszxl.taowu.cqrs.response.CategoryTwoLevelResponse;
import com.whoiszxl.taowu.entity.Category;

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
     * 一级分类接口
     * @return
     */
    List<CategoryOneLevelResponse> oneLevel();

    /**
     * 获取一级分类下的二三级分类列表
     * @param categoryId
     * @return
     */
    List<CategoryTwoLevelResponse> child(Long categoryId);

}
