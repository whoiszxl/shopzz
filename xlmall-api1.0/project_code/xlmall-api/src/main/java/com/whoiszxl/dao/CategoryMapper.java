package com.whoiszxl.dao;

import java.util.List;

import com.whoiszxl.entity.Category;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
    
    List<Category> selectCategoryChildrenByParentId(Integer parentId);
}