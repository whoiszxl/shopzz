package com.whoiszxl.service;

import java.util.List;

import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Category;

/**
 * 
 * @author whoiszxl
 *
 */
public interface CategoryService {
	ServerResponse<String> addCategory(String categoryName, Integer parentId);
    ServerResponse<String> updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);	
}	