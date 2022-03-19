package com.whoiszxl.zero.service;

import com.whoiszxl.zero.entity.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    /**
     * 树状查找分类列表
     * @return 分类列表
     */
    List<CategoryDTO> listWithTree();

}
