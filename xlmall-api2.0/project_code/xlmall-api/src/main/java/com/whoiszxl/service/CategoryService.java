package com.whoiszxl.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Category;
import com.whoiszxl.vo.CategoryVo;

/**
 * 
 * @author whoiszxl
 *
 */
public interface CategoryService {
	ServerResponse<String> addCategory(String categoryName, String img, Integer parentId);
    ServerResponse<String> updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<CategoryVo>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
	ServerResponse<String> saveOrUpdateCategory(Category category);
	ServerResponse<CategoryVo> manageCategoryDetail(Integer categoryId);
	ServerResponse<List<HashMap<String, Object>>> getIndexPageCategorys(int categoryMainCount);	
}	