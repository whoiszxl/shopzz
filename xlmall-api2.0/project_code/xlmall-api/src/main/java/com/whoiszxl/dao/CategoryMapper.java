package com.whoiszxl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.whoiszxl.entity.Category;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
    
    List<Category> selectCategoryChildrenByParentId(Integer parentId);
    
    /**
     * 通过分类父id查询count个分类出来
     * @param parentId 父id
     * @param count 需要查询的数量
     * @return 查询出来的分类List集合
     */
    List<Category> selectCategoryChildrenByParentIdAndCount(@Param("parentId")Integer parentId,@Param("count")Integer count);
}